package com.checkout.payments;

import static com.checkout.CardSourceHelper.getCardSourcePayment;
import static com.checkout.CardSourceHelper.getIndividualSender;
import static com.checkout.CardSourceHelper.getRequestCardSource;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.checkout.PlatformType;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.RequestCardSource;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.sender.PaymentIndividualSender;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

class PaymentsLoadTest extends AbstractPaymentsTestIT {

    public PaymentsLoadTest() {
        super(PlatformType.DEFAULT);
    }

    final int _numThreads = 10;
    final int _callsPerThread = 100;
    private int _callSimpleTest = 1000;

    // Inner classes for metrics
    private static class ThreadMetrics {
        final long totalResponseTime;
        final int successCount;
        final int failureCount;

        ThreadMetrics(long totalResponseTime, int successCount, int failureCount) {
            this.totalResponseTime = totalResponseTime;
            this.successCount = successCount;
            this.failureCount = failureCount;
        }
    }

    private static class PerformanceMetrics {
        final String mode;
        final long totalElapsedTime;
        final long totalResponseTime;
        final int successCount;
        final int failureCount;
        final int numThreads;

        PerformanceMetrics(String mode, long totalElapsedTime, long totalResponseTime, 
                          int successCount, int failureCount, int numThreads) {
            this.mode = mode;
            this.totalElapsedTime = totalElapsedTime;
            this.totalResponseTime = totalResponseTime;
            this.successCount = successCount;
            this.failureCount = failureCount;
            this.numThreads = numThreads;
        }

        long getAverageResponseTime() {
            int totalCalls = successCount + failureCount;
            return totalCalls > 0 ? totalResponseTime / totalCalls : 0;
        }

        double getThroughput() {
            return totalElapsedTime > 0 ? (successCount * 1000.0) / totalElapsedTime : 0;
        }
    }   

    // Multi-threaded load test - disabled by default, enable when needed to test performance
    @Disabled
    @Test
    void createPaymentsSimpleAsyncTest() {
        long totalTime = 0;        

        for(int i = 0; i < _callSimpleTest; i++) {
            final PaymentRequest request = createPaymentRequest();

            long callStart = System.currentTimeMillis();
            blocking(() -> checkoutApi.paymentsClient().requestPayment(request));
            long callEnd = System.currentTimeMillis();
            
            totalTime += (callEnd - callStart);
        }        

        printTotalTime(totalTime, _callSimpleTest, "ASYNC");
    }

    // Multi-threaded load test - disabled by default, enable when needed to test performance
    @Disabled
    @Test
    void createPaymentsSimpleSyncTest() {
        long totalTime = 0;

        for(int i = 0; i < _callSimpleTest; i++) {
            final PaymentRequest request = createPaymentRequest();
            
            long callStart = System.currentTimeMillis();
            checkoutApi.paymentsClient().requestPaymentSync(request);
            long callEnd = System.currentTimeMillis();

            totalTime += (callEnd - callStart);
        }        

        printTotalTime(totalTime, _callSimpleTest, "SYNC");
    }

    // Multi-threaded load test - disabled by default, enable when needed to test performance
    @Disabled
    @Test
    void createPaymentsMultiThreadedAsyncSyncComparisonTest() throws Exception {
        final int totalCalls = _numThreads * _callsPerThread;

        System.out.println("\n======================================================================================");
        System.out.println("MULTI-THREADED LOAD TEST - " + _numThreads + " threads, " + _callsPerThread + " calls per thread");
        System.out.println("Total API calls per test: " + totalCalls);
        System.out.println("======================================================================================\n");

        // Test ASYNC mode
        System.out.println("Starting ASYNC Multi-Threaded Test");
        PerformanceMetrics asyncMetrics = runMultiThreadedTest(_numThreads, _callsPerThread, true);
        
        // Test SYNC mode
        System.out.println("Starting SYNC Multi-Threaded Test");
        PerformanceMetrics syncMetrics = runMultiThreadedTest(_numThreads, _callsPerThread, false);

        // Print comparison
        printComparisonResults(asyncMetrics, syncMetrics, totalCalls);
    }

    // Common methods
    private PaymentRequest createPaymentRequest() {
        final Boolean three3ds = false;
        final RequestCardSource source = getRequestCardSource();
        final PaymentIndividualSender sender = getIndividualSender();
        final PaymentRequest request = getCardSourcePayment(source, sender, three3ds);
        return request;
    }
    
    private void printTotalTime(long totalTime, long numcalls, String testType) {
        final long timepercall = totalTime / numcalls;

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Total time taken to process " + numcalls + " " + testType + " payment requests: " + totalTime + " ms" + "; timepercall = " + timepercall + " ms");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private PerformanceMetrics runMultiThreadedTest(int numThreads, int callsPerThread, boolean isAsync) throws Exception {
        final String mode = isAsync ? "ASYNC" : "SYNC";
        System.out.println("\n--- Starting " + mode + " Multi-Threaded Test ---");
        
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<ThreadMetrics>> futures = new ArrayList<>();
        
        final long testStart = System.currentTimeMillis();
        
        // Submit tasks for each thread
        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            Callable<ThreadMetrics> task = () -> executePaymentCalls(threadId, callsPerThread, isAsync);
            futures.add(executor.submit(task));
        }
        
        // Collect results from all threads
        AtomicLong totalResponseTime = new AtomicLong(0);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);
        
        for (Future<ThreadMetrics> future : futures) {
            ThreadMetrics metrics = future.get();
            totalResponseTime.addAndGet(metrics.totalResponseTime);
            successCount.addAndGet(metrics.successCount);
            failureCount.addAndGet(metrics.failureCount);
        }
        
        executor.shutdown();
        final long testEnd = System.currentTimeMillis();
        
        // Gather the overall metrics
        final PerformanceMetrics performanceMetrics = new PerformanceMetrics(
            mode,
            testEnd - testStart,
            totalResponseTime.get(),
            successCount.get(),
            failureCount.get(),
            numThreads
        );

        return performanceMetrics;
    }

    private ThreadMetrics executePaymentCalls(int threadId, int callsPerThread, boolean isAsync) {
        long totalResponseTime = 0;
        int successCount = 0;
        int failureCount = 0;
        
        // System.out.println("Thread-" + threadId + " started (" + (isAsync ? "ASYNC" : "SYNC") + ")");


        for (int i = 0; i < callsPerThread; i++) {
            try {                
                final PaymentRequest request = createPaymentRequest();                
                final long callStart = System.currentTimeMillis();
                
                PaymentResponse response;
                if (isAsync) {
                    response = blocking(() -> checkoutApi.paymentsClient().requestPayment(request));
                } else {
                    response = checkoutApi.paymentsClient().requestPaymentSync(request);
                }
                
                final long callEnd = System.currentTimeMillis();
                totalResponseTime += (callEnd - callStart);
                
                if (response != null) {
                    successCount++;
                } else {
                    failureCount++;
                }
            } catch (Exception e) {
                failureCount++;
                //System.err.println("Thread-" + threadId + " error on call " + i + ": " + e.getMessage());
            }
        }
        
        // System.out.println("Thread-" + threadId + " completed (" + (isAsync ? "ASYNC" : "SYNC") + ") - Success: " + successCount + ", Failures: " + failureCount);
        
        return new ThreadMetrics(totalResponseTime, successCount, failureCount);
    }

    private void printComparisonResults(PerformanceMetrics pm1, PerformanceMetrics pm2, int totalCalls) {
        System.out.println("\n======================================================================================");
        System.out.println("PERFORMANCE COMPARISON RESULTS");
        System.out.println("======================================================================================");
        System.out.println(String.format("%-20s | %-15s | %-15s", "Metric", pm1.mode, pm2.mode));
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println(String.format("%-20s | %-15d | %-15d", "Total Calls", pm1.successCount + pm1.failureCount, pm2.successCount + pm2.failureCount));
        System.out.println(String.format("%-20s | %-15d | %-15d", "Successful", pm1.successCount, pm2.successCount));
        System.out.println(String.format("%-20s | %-15d | %-15d", "Failed", pm1.failureCount, pm2.failureCount));
        System.out.println(String.format("%-20s | %-15d | %-15d", "Threads Used", pm1.numThreads, pm2.numThreads));
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println(String.format("%-20s | %-15d ms | %-15d ms", "Total Time", pm1.totalElapsedTime, pm2.totalElapsedTime));
        System.out.println(String.format("%-20s | %-15d ms | %-15d ms", "Avg Response Time", pm1.getAverageResponseTime(), pm2.getAverageResponseTime()));
        System.out.println(String.format("%-20s | %-15.2f | %-15.2f", "Throughput (RPS)", pm1.getThroughput(), pm2.getThroughput()));
        System.out.println("--------------------------------------------------------------------------------------");
        
        // Calculate performance difference
        double speedup = (double) pm2.totalElapsedTime / pm1.totalElapsedTime;
        String winner = speedup > 1.0 ? pm1.mode : pm2.mode;
        String loser = speedup > 1.0 ? pm2.mode : pm1.mode;
        double percentage = Math.abs((speedup - 1.0) * 100);
        
        System.out.println("\nPERFORMANCE SUMMARY:");
        System.out.println(String.format("  %s is %.2f%% faster than %s", 
            winner, 
            percentage,
            loser));
        System.out.println(String.format("  Speedup factor: %.2fx", speedup > 1.0 ? speedup : 1.0 / speedup));
        System.out.println("======================================================================================\n");
    }
}


