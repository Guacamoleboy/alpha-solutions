// Pathing
// _______
// src/api/client.test.js

import { afterEach, describe, expect, it, vi } from 'vitest'
import { client } from './client'

describe('Check if API connection is correct and available', () => {
    
    // After Each
    afterEach(() => {
        vi.restoreAllMocks()
    })

    // Test
    it('connects to the API using the correct URL', async () => {
        const fetchSpy = vi.spyOn(globalThis, 'fetch')
        const response = await client('/status')
        expect(fetchSpy).toHaveBeenCalledWith(
            'http://localhost:7070/v1/status',
            expect.any(Object)
        )
        expect(response).toBeDefined()
    })

})