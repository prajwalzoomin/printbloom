# `CostCalculationService.java`

## 1. Purpose
This service handles all the math related to pricing a print job. Whenever a user uploads a document or attempts to create an order, this service calculates the total estimated cost based on the document's length and the user's printing preferences.

## 2. Key Concepts

### `@Service` Annotation
Like `@Controller` and `@Repository`, `@Service` is a Spring Boot stereotype annotation. It tells Spring: *"This class contains business logic. Please create an instance of it and manage it so other classes can use it."* 

### Hardcoded Constants
```java
private static final double BLACK_WHITE_PRICE_PER_PAGE = 2.0;
private static final double COLOR_PRICE_PER_PAGE = 5.0;
```
These are basic pricing rules. Currently, they are hardcoded into the service (₹2 for B&W, ₹5 for Color). In a more advanced version, these could be moved to the database so an admin could change prices without editing code.

## 3. Code Walkthrough

**`calculateCost()`**:
- **Inputs**: Number of pages, Print Type (Color/B&W), Number of Copies, and a Duplex flag.
- **Validation**: Checks if the page count or copies are 0 or less. If so, it safely returns `0.0`.
- **Base Cost**: It multiplies the `pageCount` by the appropriate rate depending on the `PrintType`.
- **Total Cost**: It multiplies the base cost by the total number of `copies` requested and returns the final amount. (Note: Duplex printing currently does not apply a discount, but the variable is passed in case future features require it).
