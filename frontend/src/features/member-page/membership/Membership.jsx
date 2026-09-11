// Pathing
// _______
// src/features/member-page/membership/Membership.jsx

import styles from './Membership.module.css'
import Submit from '@/shared/components/submit/Submit'
import { updateMembership } from '@/api/endpoints/membership'

const Membership = () => {

    const handleMembershipClick = async (e) => {
        e.preventDefault()
        await updateMembership();
    }

    const currentMembershipId = 2

    // Placeholders for now. Changes to API data or /data/ later
    const membershipData = [
        {
            id: 0,
            label: 'Free',
            path: '/membership/update',
            price: 0,
            priceLabel: 'Betal pr. booking',
            features: [
                'Betal kun, når du booker',
                'Medlemsprofil'
            ]
        },
        {
            id: 1,
            label: 'Basic',
            path: '/membership/update',
            price: 99,
            priceLabel: '99 kr. / måned',
            features: [
                'Alt fra free',
                '1 gratis booking per uge.',
                'Adgang til booking af udstyr',
            ]
        },
        {
            id: 2,
            label: 'Premium',
            path: '/membership/update',
            price: 199,
            priceLabel: '199 kr. / måned',
            features: [
                'Alt fra Basic',
                'Prioriteret booking',
                'Adgang til premium tider',
                'Medlemsrabatter',
                'Gratis vand hver træning'
            ]
        },
        {
            id: 3,
            label: 'Super Premium',
            path: '/member/membership',
            price: 299,
            priceLabel: '299 kr. / måned',
            features: [
                'Alt fra Premium',
                'Fri booking',
                'Eksklusive events',
                'Gratis gæstepas'
            ]
        }
    ]

    return (
        <div className={styles.membershipWrapper}>
            {membershipData.map((membership) => {

                const isCurrent = membership.id === currentMembershipId

                return (
                    <div
                        key={membership.id}
                        className={`${styles.membershipCard} ${isCurrent ? styles.current : ''}`}
                    >

                        {/* TITLE */}
                        <h2>{membership.label}</h2>

                        {/* PRICE */}
                        <p>{membership.priceLabel}</p>

                        {/* FEATURES */}
                        <ul>
                            {membership.features.map((feature) => (
                                <li key={feature}>
                                    {feature}
                                </li>
                            ))}
                        </ul>

                        {/* SALES */}
                        <div className={styles.heroAction}>
                            <Submit
                                label={isCurrent ? 'Nuværende' : 'Vælg'}
                                size="m"
                                className={styles.heroSubmitBtn}
                                onClick={isCurrent ? undefined : handleMembershipClick}
                            />
                        </div>

                    </div>
                )
            })}
        </div>
    )
}

export default Membership