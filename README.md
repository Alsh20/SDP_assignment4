# Online Order Facade

## What happens

1. **InventoryService** checks stock, calculates the total, and reserves all requested items
2. **PaymentProcessor** approves the payment or simulates a decline
3. If payment is declined, **OrderFacade** calls **InventoryService.release** and returns the payment error
4. After an approved payment, **ShippingService** creates a tracking number
5. **NotificationService** prints a confirmation
6. The facade returns an **OrderReceipt**

## Compile and run

~~~
$files = Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName
javac --release 17 -d out $files
java -cp out edu.example.order.DemoClient
~~~