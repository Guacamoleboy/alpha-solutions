// Pathing
// _______
// src/shared/utils/token.js

// -----------------------------------------------------------------------------------------------------------

// How does it work?
// _________________
//
//      - JWT is 3 parts - header.payload.signature
//      - token.split() splits on "." which is each part.
//      - split on [1] takes the payload only - which is what we want
//      - atob() decodes base64 to text. btoa() does the opposite.

// -----------------------------------------------------------------------------------------------------------

export const decodeToken = (token) => {
    try {
        const payload = token.split(".")[1]
        return JSON.parse(atob(payload))
    } catch {
        return null
    }
}