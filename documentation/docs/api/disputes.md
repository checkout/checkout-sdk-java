---
id: disputes
title: Disputes
sidebar_position: 2
---

You can find a list of request body parameters and possible outcomes [here](https://api-reference.checkout.com/#tag/Disputes).

export const Highlight = ({children, color}) => (
<span
style={{
color: color,
padding: '0.2rem',
}}>
{children}
</span>
);

## Get disputes

Returns a list of all disputes against your business. The results will be returned in reverse chronological order, showing the last modified dispute (for example, where you've recently added a piece of evidence) first. You can use the optional parameters below to skip or limit results.

```java
//You can use multiple fields in filter object such as a query parameter request
DisputesQueryFilter query = DisputesQueryFilter
        .builder()
        .paymentId("pay_123456789ASSDQWE")
        .limit(250)
        .build();

DisputesQueryResponse response = api.disputesClient().query(query).get();
```

## Get dispute details

Returns all the details of a dispute using the dispute identifier.

```java
DisputeDetailsResponse response = api.disputesClient().getDisputeDetails("dsp_123456789ASDQWE").get();
```


## Accept dispute**

If a dispute is legitimate, you can choose to accept it. This will close it for you and remove it from your list of open disputes. There are no further financial implications.

```java
Void response = api.disputesClient().accept("dsp_123456789ASDQWE").get();
```

## Provide dispute evidence

Adds supporting evidence to a dispute. Before using this endpoint, you first need to upload your files using the file uploader. You will receive a file id (prefixed by file_) which you can then use in your request. Note that this only attaches the evidence to the dispute, it does not send it to us. Once ready, you will need to submit it.

You must provide at least one evidence type in the body of your request.

```java
DisputeEvidenceRequest evidenceRequest = DisputeEvidenceRequest.builder()
                .proofOfDeliveryOrServiceFile(fileDetailsResponse.getId())
                .proofOfDeliveryOrServiceText("proof of delivery or service text")
                .invoiceOrReceiptFile(fileDetailsResponse.getId())
                .invoiceOrReceiptText("Copy of the invoice")
                .invoiceShowingDistinctTransactionsFile(fileDetailsResponse.getId())
                .invoiceShowingDistinctTransactionsText("Copy of invoice #1244 showing two transactions")
                .customerCommunicationFile(fileDetailsResponse.getId())
                .customerCommunicationText("Copy of an email exchange with the cardholder")
                .refundOrCancellationPolicyFile(fileDetailsResponse.getId())
                .refundOrCancellationPolicyText("Copy of the refund policy")
                .recurringTransactionAgreementFile(fileDetailsResponse.getId())
                .recurringTransactionAgreementText("Copy of the recurring transaction agreement")
                .additionalEvidenceFile(fileDetailsResponse.getId())
                .additionalEvidenceText("Scanned document")
                .proofOfDeliveryOrServiceDateFile(fileDetailsResponse.getId())
                .proofOfDeliveryOrServiceDateText("Copy of the customer receipt showing the merchandise was delivered on 2018-12-20")
                .build();

Void response = api.disputesClient().putEvidence("dsp_123456789ASDQWE").get();
```

## Get dispute evidence

Retrieves a list of the evidence submitted in response to a specific dispute.

```java
DisputeEvidenceResponse response = api.disputesClient().getEvidence("dsp_123456789ASDQWE").get();
```

## Submit dispute evidence

With this final request, you can submit the evidence that you have previously provided. Make sure you have provided all the relevant information before using this request. You will not be able to amend your evidence once you have submitted it.

```java
Void response = api.disputesClient().submitEvidence("dsp_123456789ASDQWE").get();
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
        

IdResponse response = api.disputesClient().uploadFile(fileRequest).get();
```

## Get file information

Retrieve information about a file that was previously uploaded.

```java
FileDetailsResponse response = api.disputesClient().getFileDetails(fileRequest).get();
```

