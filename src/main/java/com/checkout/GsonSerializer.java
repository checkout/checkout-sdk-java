package com.checkout;

import com.checkout.accounts.payout.schedule.response.CurrencySchedule;
import com.checkout.accounts.payout.schedule.response.GetScheduleResponse;
import com.checkout.accounts.payout.schedule.response.ScheduleFrequencyMonthlyResponse;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.Currency;
import com.checkout.common.InstrumentType;
import com.checkout.common.Link;
import com.checkout.common.PaymentSourceType;
import com.checkout.events.previous.EventTypes;
import com.checkout.issuing.cards.CardType;
import com.checkout.issuing.cards.responses.AbstractCardDetailsResponse;
import com.checkout.issuing.cards.responses.PhysicalCardDetailsResponse;
import com.checkout.issuing.cards.responses.VirtualCardDetailsResponse;
import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.responses.create.AbstractCardControlResponse;
import com.checkout.issuing.controls.responses.create.MccCardControlResponse;
import com.checkout.issuing.controls.responses.create.VelocityCardControlResponse;
import com.checkout.payments.PaymentDestinationType;
import com.checkout.payments.previous.PaymentAction;
import com.checkout.payments.sender.Sender;
import com.checkout.payments.sender.SenderType;
import com.checkout.webhooks.previous.WebhookResponse;
import com.checkout.workflows.actions.WorkflowActionType;
import com.checkout.workflows.conditions.WorkflowConditionType;
import com.checkout.workflows.events.WorkflowEventTypes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import org.apache.commons.lang3.EnumUtils;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GsonSerializer implements Serializer {

    private static final List<DateTimeFormatter> DEFAULT_FORMATTERS = Arrays.asList(
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    );
    private static final Type MAP_TYPE_TOKEN = new TypeToken<Map<String, Object>>() {
    }.getType();
    private static final Type EVENT_TYPES_TYPE = new TypeToken<ItemsResponse<EventTypes>>() {
    }.getType();
    private static final Type WORKFLOWS_EVENT_TYPES_TYPE = new TypeToken<ItemsResponse<WorkflowEventTypes>>() {
    }.getType();
    private static final Type WEBHOOKS_TYPE = new TypeToken<ItemsResponse<WebhookResponse>>() {
    }.getType();
    private static final Type PREVIOUS_PAYMENT_ACTIONS_TYPE = new TypeToken<ItemsResponse<PaymentAction>>() {
    }.getType();
    private static final Type PAYMENT_ACTIONS_TYPE = new TypeToken<ItemsResponse<com.checkout.payments.PaymentAction>>() {
    }.getType();

    private static final Gson DEFAULT_GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            // Instant type adapters
            .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (Instant date, Type typeOfSrc, JsonSerializationContext context) ->
                    new JsonPrimitive(date.truncatedTo(ChronoUnit.SECONDS).toString()))
            .registerTypeAdapter(Instant.class, getInstantJsonDeserializer())
            // Payments PREVIOUS - source
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.payments.previous.response.source.ResponseSource.class, CheckoutUtils.TYPE, true, com.checkout.payments.previous.response.source.AlternativePaymentSourceResponse.class)
                    .registerSubtype(com.checkout.payments.previous.response.source.CardResponseSource.class, identifier(PaymentSourceType.CARD)))
            // Payments PREVIOUS - destination
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.payments.previous.response.destination.PaymentResponseDestination.class, CheckoutUtils.TYPE, true, com.checkout.payments.previous.response.destination.PaymentResponseAlternativeDestination.class)
                    .registerSubtype(com.checkout.payments.previous.response.destination.PaymentResponseCardDestination.class, identifier(PaymentDestinationType.CARD)))
            // Payments - source
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.payments.response.source.ResponseSource.class, CheckoutUtils.TYPE, true, com.checkout.payments.response.source.AlternativePaymentSourceResponse.class)
                    .registerSubtype(com.checkout.payments.response.source.CardResponseSource.class, identifier(PaymentSourceType.CARD))
                    .registerSubtype(com.checkout.payments.response.source.CurrencyAccountResponseSource.class, identifier(PaymentSourceType.CURRENCY_ACCOUNT)))
            // Payments - destination
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.payments.response.destination.PaymentResponseDestination.class, CheckoutUtils.TYPE, true, com.checkout.payments.response.destination.PaymentResponseAlternativeDestination.class)
                    .registerSubtype(com.checkout.payments.response.destination.PaymentResponseBankAccountDestination.class, identifier(PaymentDestinationType.BANK_ACCOUNT)))
            // Payments - sender
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(Sender.class, CheckoutUtils.TYPE, true, com.checkout.payments.sender.ResponseAlternativeSender.class)
                    .registerSubtype(com.checkout.payments.sender.PaymentCorporateSender.class, identifier(SenderType.CORPORATE))
                    .registerSubtype(com.checkout.payments.sender.PaymentIndividualSender.class, identifier(SenderType.INDIVIDUAL))
                    .registerSubtype(com.checkout.payments.sender.PaymentInstrumentSender.class, identifier(SenderType.INSTRUMENT)))
            // Instruments CS2
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.instruments.create.CreateInstrumentResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(com.checkout.instruments.create.CreateInstrumentBankAccountResponse.class, identifier(InstrumentType.BANK_ACCOUNT))
                    .registerSubtype(com.checkout.instruments.create.CreateInstrumentTokenResponse.class, identifier(InstrumentType.CARD)))
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.instruments.get.GetInstrumentResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(com.checkout.instruments.get.GetBankAccountInstrumentResponse.class, identifier(InstrumentType.BANK_ACCOUNT))
                    .registerSubtype(com.checkout.instruments.get.GetCardInstrumentResponse.class, identifier(InstrumentType.CARD)))
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.instruments.update.UpdateInstrumentResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(com.checkout.instruments.update.UpdateInstrumentBankAccountResponse.class, identifier(InstrumentType.BANK_ACCOUNT))
                    .registerSubtype(com.checkout.instruments.update.UpdateInstrumentCardResponse.class, identifier(InstrumentType.CARD)))
            // Workflows CS2
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.workflows.actions.response.WorkflowActionResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(com.checkout.workflows.actions.response.WebhookWorkflowActionResponse.class, identifier(WorkflowActionType.WEBHOOK)))
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.workflows.conditions.response.WorkflowConditionResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(com.checkout.workflows.conditions.response.EventWorkflowConditionResponse.class, identifier(WorkflowConditionType.EVENT))
                    .registerSubtype(com.checkout.workflows.conditions.response.EntityWorkflowConditionResponse.class, identifier(WorkflowConditionType.ENTITY))
                    .registerSubtype(com.checkout.workflows.conditions.response.ProcessingChannelWorkflowConditionResponse.class, identifier(WorkflowConditionType.PROCESSING_CHANNEL)))
            // Accounts CS2 - PayoutSchedules
            .registerTypeAdapter(GetScheduleResponse.class, getScheduleResponseDeserializer())
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.accounts.payout.schedule.response.ScheduleResponse.class, CheckoutUtils.FREQUENCY)
                    .registerSubtype(com.checkout.accounts.payout.schedule.response.ScheduleFrequencyDailyResponse.class, CheckoutUtils.DAILY)
                    .registerSubtype(com.checkout.accounts.payout.schedule.response.ScheduleFrequencyWeeklyResponse.class, CheckoutUtils.WEEKLY)
                    .registerSubtype(ScheduleFrequencyMonthlyResponse.class, CheckoutUtils.MONTHLY))
            // Issuing CS2 - CardDetailsResponse
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(AbstractCardDetailsResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(PhysicalCardDetailsResponse.class, identifier(CardType.PHYSICAL))
                    .registerSubtype(VirtualCardDetailsResponse.class, identifier(CardType.VIRTUAL)))
            // Issuing CS2 - CardControlsResponse
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(AbstractCardControlResponse.class, "control_type")
                    .registerSubtype(VelocityCardControlResponse.class, identifier(ControlType.VELOCITY_LIMIT))
                    .registerSubtype(MccCardControlResponse.class, identifier(ControlType.MCC_LIMIT)))
            // Adapters when API returns an array
            .registerTypeAdapter(EVENT_TYPES_TYPE, eventTypesResponseDeserializer())
            .registerTypeAdapter(WORKFLOWS_EVENT_TYPES_TYPE, workflowEventTypesResponseDeserializer())
            .registerTypeAdapter(WEBHOOKS_TYPE, webhooksResponseDeserializer())
            .registerTypeAdapter(PREVIOUS_PAYMENT_ACTIONS_TYPE, paymentActionsResponsePreviousDeserializer())
            .registerTypeAdapter(PAYMENT_ACTIONS_TYPE, paymentActionsResponseDeserializer())
            .create();

    private final Gson gson;

    public GsonSerializer() {
        this(DEFAULT_GSON);
    }

    public GsonSerializer(final Gson gson) {
        this.gson = gson;
    }

    public Gson getGson() {
        return gson;
    }

    @Override
    public <T> String toJson(final T object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T fromJson(final String json, final Class<T> type) {
        return gson.fromJson(json, type);
    }

    @Override
    public <T> T fromJson(final String json, final Type type) {
        return gson.fromJson(json, type);
    }

    @Override
    public Map<String, Object> fromJson(final String json) {
        return gson.fromJson(json, MAP_TYPE_TOKEN);
    }

    private static <E extends Enum<E>> String identifier(final E enumEntry) {
        if (enumEntry == null) {
            throw new IllegalStateException("invalid enum entry");
        }
        return enumEntry.name().toLowerCase();
    }

    private static JsonDeserializer<ItemsResponse<EventTypes>> eventTypesResponseDeserializer() {
        return (json, typeOfT, context) -> {
            final ItemsResponse<EventTypes> eventTypesResponse = new ItemsResponse<>();
            if (json.isJsonArray()) {
                eventTypesResponse.setItems(deserializeJsonArray(json, EventTypes.class));
            }
            return eventTypesResponse;
        };
    }

    private static JsonDeserializer<ItemsResponse<WorkflowEventTypes>> workflowEventTypesResponseDeserializer() {
        return (json, typeOfT, context) -> {
            final ItemsResponse<WorkflowEventTypes> workflowEventTypesResponse = new ItemsResponse<>();
            if (json.isJsonArray()) {
                workflowEventTypesResponse.setItems(deserializeJsonArray(json, WorkflowEventTypes.class));
            }
            return workflowEventTypesResponse;
        };
    }

    private static JsonDeserializer<ItemsResponse<WebhookResponse>> webhooksResponseDeserializer() {
        return (json, typeOfT, context) -> {
            final ItemsResponse<WebhookResponse> webhooksResponse = new ItemsResponse<>();
            if (json.isJsonArray()) {
                webhooksResponse.setItems(deserializeJsonArray(json, WebhookResponse.class));
            }
            return webhooksResponse;
        };
    }

    private static JsonDeserializer<ItemsResponse<PaymentAction>> paymentActionsResponsePreviousDeserializer() {
        return (json, typeOfT, context) -> {
            final ItemsResponse<PaymentAction> paymentActionsResponse = new ItemsResponse<>();
            if (json.isJsonArray()) {
                paymentActionsResponse.setItems(deserializeJsonArray(json, PaymentAction.class));
            }
            return paymentActionsResponse;
        };
    }

    private static JsonDeserializer<ItemsResponse<com.checkout.payments.PaymentAction>> paymentActionsResponseDeserializer() {
        return (json, typeOfT, context) -> {
            final ItemsResponse<com.checkout.payments.PaymentAction> paymentActionsResponse = new ItemsResponse<>();
            if (json.isJsonArray()) {
                paymentActionsResponse.setItems(deserializeJsonArray(json, com.checkout.payments.PaymentAction.class));
            }
            return paymentActionsResponse;
        };
    }

    private static <T> List<T> deserializeJsonArray(final JsonElement json, final Class<T> itemsType) {
        final JsonArray jsonArray = json.getAsJsonArray();
        return IntStream
                .range(0, json.getAsJsonArray().size())
                .mapToObj(i -> DEFAULT_GSON.fromJson(jsonArray.get(i), itemsType))
                .collect(Collectors.toList());
    }

    private static JsonDeserializer<GetScheduleResponse> getScheduleResponseDeserializer() {
        return (json, typeOfT, context) -> {
            final JsonObject jsonObject = json.getAsJsonObject();
            final EnumMap<Currency, CurrencySchedule> currency = new EnumMap<>(Currency.class);
            final GetScheduleResponse getScheduleResponse = new GetScheduleResponse();
            jsonObject.keySet().forEach(key -> {
                if (EnumUtils.isValidEnum(Currency.class, key)) {
                    final CurrencySchedule currencySchedule = DEFAULT_GSON.fromJson(jsonObject.get(key), CurrencySchedule.class);
                    currency.put(Currency.valueOf(key), currencySchedule);
                    getScheduleResponse.setCurrency(currency);
                }
                if (key.equalsIgnoreCase("_links")) {
                    final Type type = new TypeToken<Map<String, Link>>() {
                    }.getType();
                    final Map<String, Link> links = DEFAULT_GSON.fromJson(jsonObject.get(key), type);
                    getScheduleResponse.setLinks(links);
                }
            });
            return getScheduleResponse;
        };
    }

    private static JsonDeserializer<Instant> getInstantJsonDeserializer() {
        return (json, typeOfT, context) -> {
            final String dateString = json.getAsString();
            try {
                return Instant.parse(dateString);
            } catch (final DateTimeParseException ex) {
                for (final DateTimeFormatter formatter : DEFAULT_FORMATTERS) {
                    try {
                        final LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
                        return dateTime.toInstant(ZoneOffset.UTC);
                    } catch (final DateTimeParseException ignored) {
                        // continue to next formatter
                    }
                }
                throw ex;
            }
        };
    }
}
