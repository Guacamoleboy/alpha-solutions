# ADR-004: Hashing

## Status
Awaiting team approval

## Context
Our REST API needs to comply with EU regulation and data handling. This results in the need for Hashing.
Passwords, email and other sensitive information must not be stored in plaintext.

## Decision
We will use BCrypt for password and email hashing in our REST API.
Passwords and emails will be hashed before going into the database.
When a member logs in the provided password will be verified using BCrypt against the stored hash.

## Alternatives
SHA-256
MD5
SHA-512
SHA-1

## Consequences
- Data is never stored in plaintext.
- Password verification happens once a provided password is available.
- BCrypt might be slower than alternatives, but it's minor.