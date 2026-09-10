# Expect JSON output

Used at initial setup to establish entities and DTOs.

## Context

In order to re-design the workflow around DTOs, Entities and DAOs we are starting with what our expected JSON would look like.
This should give us pretty much our first DTOs and our first Entities.

NOTE: This is an estimate and not the final solution.

# Member (US-01)

- id should be String as it's most likely going to be returning a UUID.
- membership_id is used to establish the type of membership
- is_active is used to establish if the customer has been there in the past 14 days hence active or not (for planning purpose)
- last_played is to establish an initial landing page with a "Skal vi booke igen.. ?" button
- email should be either hashed or encrypted. I'm actually on the encryption team for this one if we want to add support later on.

## Expected Output

```JSON
{
  "id": "memberId",
  "first_name": "Name",
  "last_name": "Lastname",
  "email": "mail@mail.dk",
  "phone": "+4560606060",
  "date_of_birth": "1990-05-15",
  "gender": "gender",
  "membership_id": "membershipId",
  "is_active": true,
  "last_played": "date"
}
```

# Membership (US-01)

- id UUID so String output in JSON formatting
- desription VARCHAR or TEXT.
- price as Double
- duration ENUM ?
- dates in localdate or timestamp? Figure it out

## Expected Output

```JSON
{
  "id": "membershipId",
  "name": "Premium",
  "description": "This is a text...",
  "price": 99.00,
  "currency": "dkk",
  "duration": "YEARLY, MONTHLY, WEEKLY, PER SESSION",
  "start_date": "date",
  "end_date": "date",
  "guest_pass": true,
  "active": true
}
```