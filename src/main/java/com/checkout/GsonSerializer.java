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
import com.checkout.issuing.cards.responses.CardDetailsResponse;
import com.checkout.issuing.cards.responses.PhysicalCardDetailsResponse;
import com.checkout.issuing.cards.responses.VirtualCardDetailsResponse;
import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.responses.create.CardControlResponse;
import com.checkout.issuing.controls.responses.create.MccCardControlResponse;
import com.checkout.issuing.controls.responses.create.VelocityCardControlResponse;
import com.checkout.payments.PaymentDestinationType;
import com.checkout.payments.Product;
import com.checkout.payments.ProductType;
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
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import lombok.Getter;
import org.apache.commons.lang3.EnumUtils;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
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

@Getter
public class GsonSerializer implements Serializer {

    private static final List<DateTimeFormatter> DEFAULT_FORMATTERS = Arrays.asList(
            DateTimeFormatter.ISO_INSTANT,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME,
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXX"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
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
            // Payments - AbstractSource (polymorphic deserialization)
            .registerTypeAdapterFactory(
                RuntimeTypeAdapterFactory.of(
                    com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource.class,
                    CheckoutUtils.TYPE
                )
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.CardSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.CARD))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.afterpaysource.AfterpaySource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.AFTERPAY))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.alipaycnsource.AlipayCnSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.ALIPAY_CN))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.alipayhksource.AlipayHkSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.ALIPAY_HK))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.alipayplussource.AlipayPlusSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.ALIPAY_PLUS))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.almasource.AlmaSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.ALMA))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.bancontactsource.BancontactSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.BANCONTACT))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.benefitsource.BenefitSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.BENEFIT))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.currencyaccountsource.CurrencyAccountSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.CURRENCY_ACCOUNT))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.cvconnectsource.CvconnectSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.CVCONNECT))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.danasource.DanaSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.DANA))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.epssource.EpsSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.EPS))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.fawrysource.FawrySource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.FAWRY))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.gcashsource.GcashSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.GCASH))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.idealsource.IdealSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.IDEAL))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.illicadosource.IllicadoSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.ILLICADO))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.kakaopaysource.KakaopaySource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.KAKAOPAY))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.klarnasource.KlarnaSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.KLARNA))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.knetsource.KnetSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.KNET))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.mbwaysource.MbwaySource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.MBWAY))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.mobilepaysource.MobilepaySource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.MOBILEPAY))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.multibancosource.MultibancoSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.MULTIBANCO))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.octopussource.OctopusSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.OCTOPUS))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.paynowsource.PaynowSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.PAYNOW))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.paypalsource.PaypalSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.PAYPAL))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.postfinancesource.PostfinanceSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.POSTFINANCE))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.ptwofoursource.PTwoFourSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.P24))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.qpaysource.QpaySource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.QPAY))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.sepasource.SepaSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.SEPA))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.sequrasource.SequraSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.SEQURA))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.stcpaysource.StcpaySource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.STCPAY))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.tamarasource.TamaraSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.TAMARA))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.tngsource.TngSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.TNG))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.truemoneysource.TruemoneySource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.TRUEMONEY))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.twintsource.TwintSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.TWINT))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.vippssource.VippsSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.VIPPS))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.wechatpaysource.WechatpaySource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.WECHATPAY))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.paymentgetresponsegiropaysourcesource.PaymentGetResponseGiropaySourceSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.PAYMENT_GET_RESPONSE_GIROPAY_SOURCE))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.paymentgetresponseklarnasourcesource.PaymentGetResponseKlarnaSourceSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.PAYMENT_GET_RESPONSE_KLARNA_SOURCE))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.paymentgetresponsesepavfoursourcesource.PaymentGetResponseSEPAVFourSourceSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.PAYMENT_GET_RESPONSE_SEPAV4_SOURCE))
                    .registerSubtype(com.checkout.handlepaymentsandpayouts.payments.common.source.paymentresponsesourcesource.PaymentResponseSourceSource.class, identifier(com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType.PAYMENT_RESPONSE_SOURCE))
            )
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
            // Payment Contexts
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.payments.response.source.contexts.ResponseSource.class, CheckoutUtils.TYPE, true, com.checkout.payments.response.source.contexts.AlternativePaymentSourceResponse.class)
                    .registerSubtype(com.checkout.payments.response.source.contexts.PaymentContextsPayPalResponseSource.class, identifier(PaymentSourceType.PAYPAL))
                    .registerSubtype(com.checkout.payments.response.source.contexts.PaymentContextsKlarnaResponseSource.class, identifier(PaymentSourceType.KLARNA))
                    .registerSubtype(com.checkout.payments.response.source.contexts.PaymentContextsStcpayResponseSource.class, identifier(PaymentSourceType.STCPAY))
                    .registerSubtype(com.checkout.payments.response.source.contexts.PaymentContextsTabbyResponseSource.class, identifier(PaymentSourceType.TABBY)))
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
                    .registerSubtype(com.checkout.instruments.create.CreateInstrumentTokenResponse.class, identifier(InstrumentType.CARD))
                    .registerSubtype(com.checkout.instruments.create.CreateInstrumentSepaResponse.class, identifier(InstrumentType.SEPA)))
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(com.checkout.instruments.get.GetInstrumentResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(com.checkout.instruments.get.GetBankAccountInstrumentResponse.class, identifier(InstrumentType.BANK_ACCOUNT))
                    .registerSubtype(com.checkout.instruments.get.GetCardInstrumentResponse.class, identifier(InstrumentType.CARD))
                    .registerSubtype(com.checkout.instruments.get.GetSepaInstrumentResponse.class, identifier(InstrumentType.SEPA)))
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
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(CardDetailsResponse.class, CheckoutUtils.TYPE)
                    .registerSubtype(PhysicalCardDetailsResponse.class, identifier(CardType.PHYSICAL))
                    .registerSubtype(VirtualCardDetailsResponse.class, identifier(CardType.VIRTUAL)))
            // Issuing CS2 - CardControlsResponse
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(CardControlResponse.class, CheckoutUtils.CONTROL_TYPE)
                    .registerSubtype(VelocityCardControlResponse.class, identifier(ControlType.VELOCITY_LIMIT))
                    .registerSubtype(MccCardControlResponse.class, identifier(ControlType.MCC_LIMIT)))
            // Adapters when API returns an array
            .registerTypeAdapter(EVENT_TYPES_TYPE, eventTypesResponseDeserializer())
            .registerTypeAdapter(WORKFLOWS_EVENT_TYPES_TYPE, workflowEventTypesResponseDeserializer())
            .registerTypeAdapter(WEBHOOKS_TYPE, webhooksResponseDeserializer())
            .registerTypeAdapter(PREVIOUS_PAYMENT_ACTIONS_TYPE, paymentActionsResponsePreviousDeserializer())
            .registerTypeAdapter(PAYMENT_ACTIONS_TYPE, paymentActionsResponseDeserializer())
            .registerTypeAdapter(Product.class, getProductDeserializer())
            .create();

    private final Gson gson;

    public GsonSerializer() {
        this(DEFAULT_GSON);
    }

    public GsonSerializer(final Gson gson) {
        this.gson = gson;
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
            String dateString;

            if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber()) {
                dateString = String.valueOf(json.getAsLong());
            } else {
                dateString = json.getAsString();
            }

            try {
                return Instant.parse(dateString);
            } catch (final DateTimeParseException ex) {
                if (dateString.matches("\\d{8}")) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                        LocalDateTime dateTime = LocalDate.parse(dateString, formatter).atStartOfDay();
                        return dateTime.toInstant(ZoneOffset.UTC);
                    } catch (final DateTimeParseException e) {
                        throw new JsonParseException("Failed to parse numeric date in format yyyyMMdd: " + dateString, e);
                    }
                }
                if (dateString.length() == 10) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate date = LocalDate.parse(dateString, formatter);
                        return date.atStartOfDay().toInstant(ZoneOffset.UTC);
                    } catch (final DateTimeParseException e) {
                        throw new JsonParseException("Failed to parse date in format yyyy-MM-dd: " + dateString, e);
                    }
                }
                for (final DateTimeFormatter formatter : DEFAULT_FORMATTERS) {
                    try {
                        final LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
                        return dateTime.toInstant(ZoneOffset.UTC);
                    } catch (final DateTimeParseException ignored) {
                    }
                }
                throw ex;
            }
        };
    }

    private static JsonDeserializer<Product> getProductDeserializer() {
        return (json, typeOfT, context) -> {
            Product product = new Product();
            JsonObject jsonObject = json.getAsJsonObject();

            JsonElement typeElement = jsonObject.get("type");
            Object typeValue = null;

            if (typeElement != null && typeElement.isJsonPrimitive()) {
                String typeAsString = typeElement.getAsString();
                if (EnumUtils.isValidEnumIgnoreCase(ProductType.class, typeAsString)) {
                    typeValue = ProductType.valueOf(typeAsString.toUpperCase());
                } else {
                    typeValue = typeAsString;
                }
            }
            product.setType(typeValue);


            jsonObject.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("type"))
                .forEach(entry -> {
                    try {
                        String jsonKey = entry.getKey();
                        JsonElement jsonValue = entry.getValue();

                        java.lang.reflect.Field field = Arrays.stream(Product.class.getDeclaredFields())
                                .filter(f -> {
                                    SerializedName annotation = f.getAnnotation(SerializedName.class);
                                    return (annotation != null && annotation.value().equals(jsonKey)) || f.getName().equals(jsonKey);
                                })
                                .findFirst()
                                .orElse(null);

                        if (field != null) {
                            field.setAccessible(true);
                            Class<?> fieldType = field.getType();

                            if (jsonValue.isJsonNull()) {
                                field.set(product, null);
                            } else if (fieldType.equals(String.class)) {
                                field.set(product, jsonValue.getAsString());
                            } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
                                field.set(product, jsonValue.getAsLong());
                            } else if (fieldType.equals(Instant.class)) {
                                String dateString = jsonValue.getAsString();
                                if (dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                    dateString += "T00:00:00Z";
                                }
                                field.set(product, Instant.parse(dateString));
                            } else {
                                Object nestedObject = context.deserialize(jsonValue, fieldType);
                                field.set(product, nestedObject);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        System.err.println("Error setting field: " + entry.getKey() + ", " + e.getMessage());
                    }
                });

            return product;
        };
    }
}
