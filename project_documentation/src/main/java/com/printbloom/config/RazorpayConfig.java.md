# `RazorpayConfig.java`

## 1. Purpose
This file is responsible for setting up the connection to the Razorpay payment gateway. To accept real payments (or simulate them in a test environment), our application needs to securely prove its identity to Razorpay using an API Key and a Secret. 

## 2. Key Concepts

### `@Bean` Annotation
In Spring Boot, an object that is managed by the Spring framework is called a "Bean". 
When you annotate a method with `@Bean`, you are telling Spring: *"Run this method, take whatever object it returns, and save it in your central memory. Whenever another part of my app asks for this object, give them this exact one."*

## 3. Code Walkthrough

1. **Reading Credentials**:
   ```java
   @Value("${razorpay.key.id}")
   private String keyId;

   @Value("${razorpay.key.secret}")
   private String keySecret;
   ```
   These lines read your highly sensitive Razorpay credentials from `application.properties`. 

2. **Initializing the Client**:
   ```java
   @Bean
   public RazorpayClient razorpayClient() throws RazorpayException {
       return new RazorpayClient(keyId, keySecret);
   }
   ```
   This method creates a new `RazorpayClient` using your `keyId` and `keySecret`. Because of the `@Bean` annotation, Spring takes this fully configured client and keeps it ready. Later, when the `RazorpayService` needs to create an order or verify a payment, it simply asks Spring for this pre-configured client, saving us from having to recreate the connection over and over again.
