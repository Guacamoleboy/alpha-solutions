# JWT Learning

> Trying to understand things better - simply by creating .md files for learning purposes.
> This is my JWT learning .md file for me to better understand JWT and what JWT does and how it works.

___

## What is JWT and what are the visuals of a payload?

HEADER.PAYLOAD.SIGNATURE\
3 Parts.

Example:

>eyJhbGciOiJIUzI1NiJ9\
>.\
>eyJzdWIiOiI3MWFmMjdkMy1kYjJiLTQ2ZWQtYjY4MS1kZDNkMDQ4MWVlOWQiLCJ1c2VybmFtZSI6ImJydWdlcjEiLCJyb2xlIjoiYWNjZXNzIiwiaWF0IjoxNzc1MTI5NzMxLCJleHAiOjE3NzUxMzAzMzF9\
>.\
>hbP9T3_ujWN7H-i8PWaOG5WWXb3QAlH4E8o44kCNGw0

or as JSON format when **logging in**

>"access_token":
>"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3MWFmMjdkMy1kYjJiLTQ2ZWQtYjY4MS1kZDNkMDQ4MWVlOWQiLCJ1c2VybmFtZSI6ImJydWdlcjEiLCJyb2xlIjoiYWNjZXNzIiwiaWF0IjoxNzc1MTI5NzMxLCJleHAiOjE3NzUxMzAzMzF9.hbP9T3_ujWN7H-i8PWaOG5WWXb3QAlH4E8o44kCNGw0"
___

## What is a claim?

A claim is the payload. See it as JSON data. The claim is basically JSON formatting of data.\
So for example if we were to .claim(x, x) we would se x to x in our claim JSON formatting.

By adding a getClaims and getter for each field we can pull the data directly from the claim itself.\
Smart.

___

## Important JWT Methods and their functionality

> .parseSignedClaims(CharSequence jwt)

> **Functionality**
>
> I like to see it as Jackson for JSON. It takes the JWT Token (HEADER.PAYLOAD.SIGNATURE), checks it, verifies it,
> decodes the payload into claims (JSON formatting output)

_

> .signWith(Key key)

> **Functionality**
>
> Signs the key with a specific key for safety reasons. For example JWT_SECRET in my .env file.

_

> .verifyWith(Key key)

> **Functionality**
>
> Verifies the token using the unique key. Making sure no data has been tampered with. Extra safety. Without signWith
> it would have no use.

_

> .setSubject(String sub)

> **Functionality**
>
> Sets the owner of the key. Often the ID of the User / Client whatever. In my case it's the UUID of the user.
>
> Output is sub: in JSON formatting.

_

> .compact()

> **Functionality**
>
> Outputs the key as String in one line for ease of use

___

## Terminology

> CharSequence is an **interface** used for **polymorphism** for data with Char (characters). Common implementations.
> - String
> - StringBuilder
> - StringBuffer
> - CharBuffer

## Notes for myself

- N/A

___

> Created by: Guacamoleboy
>
> Last edited: 14/09-2026
