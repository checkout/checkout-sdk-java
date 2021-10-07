---
id: disputes
title: Disputes
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/preview/crusoe/#tag/Disputes).

## Get disputes

Returns a list of all disputes against your business. The results will be returned in reverse chronological order, showing the last modified dispute (for example, where you've recently added a piece of evidence) first. You can use the optional parameters below to skip or limit results.

```java
//You can use multiple fields in filter object such as a query parameter request
DisputesQueryFilter query = DisputesQueryFilter
        .builder()
        .paymentId()
        .limit()
        .build();

DisputesQueryResponse response = fourApi.disputesClient().query(query).get();
```

## Get dispute details

Returns all the details of a dispute using the dispute identifier.

```java
DisputeDetailsResponse response = fourApi.disputesClient().getDisputeDetails(disputeId).get();
```

## Accept dispute

If a dispute is legitimate, you can choose to accept it. This will close it for you and remove it from your list of open disputes. There are no further financial implications.

```java
fourApi.disputesClient().accept(disputeId).get();
```

## Provide dispute evidence

Adds supporting evidence to a dispute. Before using this endpoint, you first need to upload your files using the file uploader. You will receive a file id (prefixed by file_) which you can then use in your request. Note that this only attaches the evidence to the dispute, it does not send it to us. Once ready, you will need to submit it.

You must provide at least one evidence type in the body of your request.

```java
DisputeEvidenceRequest evidenceRequest = DisputeEvidenceRequest.builder()
                .proofOfDeliveryOrServiceFile()
                .proofOfDeliveryOrServiceText()
                .invoiceOrReceiptFile()
                .invoiceOrReceiptText()
                .invoiceShowingDistinctTransactionsFile()
                .invoiceShowingDistinctTransactionsText()
                .customerCommunicationFile()
                .customerCommunicationText()
                .refundOrCancellationPolicyFile()
                .refundOrCancellationPolicyText()
                .recurringTransactionAgreementFile()
                .recurringTransactionAgreementText()
                .additionalEvidenceFile()
                .additionalEvidenceText()
                .proofOfDeliveryOrServiceDateFile()
                .proofOfDeliveryOrServiceDateText()
                .build();

fourApi.disputesClient().putEvidence(disputeId).get();
```

## Get dispute evidence

Retrieves a list of the evidence submitted in response to a specific dispute.

```java
DisputeEvidenceResponse response = fourApi.disputesClient().getEvidence(disputeId).get();
```

## Submit dispute evidence

With this final request, you can submit the evidence that you have previously provided. Make sure you have provided all the relevant information before using this request. You will not be able to amend your evidence once you have submitted it.

```java
fourApi.disputesClient().submitEvidence(disputeId).get();
```

## Upload file

Upload a file to use as evidence in a dispute. Your file must be in either JPEG/JPG, PNG or PDF format, and be no larger than 4MB.

```java
//Note, for PDF we need to create manually, for JPEG we can use ContentType.IMAGE_JPEG
FileRequest fileRequest = FileRequest.builder()
                .file(file)
                .contentType(ContentType.create("application/pdf"))
                .purpose(FilePurpose.DISPUTE_EVIDENCE)
                .build();
        

IdResponse response = fourApi.disputesClient().uploadFile(fileRequest).get();
```

## Get file information

Retrieve information about a file that was previously uploaded.

```java
FileDetailsResponse response = fourApi.disputesClient().getFileDetails(fileRequest).get();
```

