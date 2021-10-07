---
id: workflows
title: Workflows
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/preview/crusoe/#tag/Workflows).

## Get all workflows

```java
GetWorkflowsResponse response = fourApi.workflowsClient().getWorkflows().get();
```

## Get a workflow

```java
GetWorkflowsResponse response = fourApi.workflowsClient().getWorkflow("workflowId").get();
```

## Add a workflow

```java
CreateWorkflowRequest request = CreateWorkflowRequest.builder()
    .name()
    .actions()
    .conditions()
    .build();

CreateWorkflowResponse response = fourApi.workflowsClient().createWorkflow(request).get();
```

## Remove a workflow

Removes a workflow so it is no longer being executed. Actions of already executed workflows will be still processed.

```java
fourApi.workflowsClient().removeWorkflow(workflowId).get();
```

## Patch a workflow

Update the name of a workflow.

```java
UpdateWorkflowRequest request = UpdateWorkflowRequest.builder()
        .name()
        .build();

UpdateWorkflowResponse response = fourApi.workflowsClient().updateWorkflow(workflowId, request).get();
```

## Update a workflow action

```java
WebhookWorkflowActionRequest request = WebhookWorkflowActionRequest.builder()
    .signature()
    .headers()
    .url()
    .build();

fourApi.workflowsClient().updateWorkflowAction(workflowId, actionId, request).get();
```

## Update a workflow condition

```java
EventWorkflowConditionRequest request = EventWorkflowConditionRequest.builder()
    .events()
    .build();

fourApi.workflowsClient().updateWorkflowCondition(workflowResponseId, eventConditionId, request).get();
```

## Get event types

```java
List<EventTypesResponse> response = fourApi.workflowsClient().getEventTypes().get();
```

## Get an event

```java
GetEventResponse response = fourApi.workflowsClient().getEvent(eventId).get();
```

## Get subject events

Get all events that relate to a specific subject

```java
SubjectEventsResponse response = fourApi.workflowsClient().getSubjectEvents(payment.getId()).get();
```

## Reflow by event

Reflows a past event denoted by the event ID and triggers the actions of any workflows with matching conditions.

```java
ReflowResponse response = fourApi.workflowsClient().reflowByEvent(eventId).get();
```

## Reflow by event and workflow

Reflows a past event by event ID and workflow ID. Triggers all the actions of a specific event and workflow combination if the event denoted by the event ID matches the workflow conditions.

```java
ReflowResponse response = fourApi.workflowsClient().reflowByEventAndWorkflow(eventId, workflowId).get();
```

## Reflow

Reflow past events attached to multiple event IDs and workflow IDs, or to multiple subject IDs and workflow IDs. If you don't specify any workflow IDs, all matching workflows will be retriggered.

```java
ReflowByEventsRequest request = ReflowByEventsRequest.builder()
        .events()
        .workflows()
        .build();

ReflowResponse response = fourApi.workflowsClient().reflow(request).get();
```

## Reflow by subject

Reflows the events associated with a subject ID (for example, a payment ID or a dispute ID) and triggers the actions of any workflows with matching conditions.

```java
ReflowByEventsRequest request = ReflowByEventsRequest.builder()
        .events()
        .workflows()
        .build();

ReflowResponse response = fourApi.workflowsClient().reflowBySubject("subject").get();
```

## Reflow by subject and workflow

Reflows the events associated with a subject ID (for example, a payment ID or a dispute ID) and triggers the actions of the specified workflow if the conditions match.

```java
ReflowByEventsRequest request = ReflowByEventsRequest.builder()
        .events()
        .workflows()
        .build();

ReflowResponse response = fourApi.workflowsClient().reflowBySubjectAndWorkflow("subject", workflowId).get();
```

