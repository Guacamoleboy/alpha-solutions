// Pathing
// _______
// src/features/member-page/membership/Membership.jsx

import {Link} from 'react-router-dom'
import memberImageStyles from '../MemberImageCard.module.css'
import {useMembership} from './Membership.hooks'
import styles from './Membership.module.css'

const Membership = () => {
    const {member, handleMembershipChange} = useMembership()
    const currentMembershipId = member?.membership_id

    const membershipData = [
        {id: 1, label: 'Free', path: '/membership/update', priceLabel: 'Betal pr. booking', features: ['Betal kun, når du booker', 'Medlemsprofil']},
        {id: 2, label: 'Basic', path: '/membership/update', priceLabel: '99 kr. / måned', features: ['Alt fra Free', '1 gratis booking per uge.', 'Adgang til booking af udstyr']},
        {id: 3, label: 'Premium', path: '/membership/update', priceLabel: '199 kr. / måned', features: ['Alt fra Basic', 'Prioriteret booking', 'Adgang til premium tider', 'Medlemsrabatter', 'Opret og anmod om events', 'Gratis vand hver træning']},
        {id: 4, label: 'Super Premium', path: '/member/membership', priceLabel: '299 kr. / måned', features: ['Alt fra Premium', 'Fri booking', 'Eksklusive events', 'Gratis gæstepas']},
    ]

    const handleMembershipClick = async (event, membershipId) => {
        event.preventDefault()
        await handleMembershipChange(membershipId)
    }

    return (
        <div className={styles.membershipWrapper}>
            {membershipData.map((membership) => {
                const isCurrent = membership.id === currentMembershipId
                const content = (
                    <>
                        <h2>{membership.label}</h2>
                        <p>{membership.priceLabel}</p>
                        <ul>
                            {membership.features.map((feature) => <li key={feature}>{feature}</li>)}
                        </ul>
                    </>
                )

                if (isCurrent) {
                    return <div key={membership.id} className={`${styles.membershipCard} ${styles.current}`}>{content}</div>
                }

                return (
                    <Link
                        key={membership.id}
                        className={styles.membershipCard}
                        to={membership.path}
                        onClick={(event) => handleMembershipClick(event, membership.id)}
                        aria-label={`Vælg ${membership.label} medlemskab`}
                    >
                        {content}
                        <span className={memberImageStyles.actionIcon} aria-hidden="true">
                            <i className="fa fa-arrow-up" />
                        </span>
                    </Link>
                )
            })}
        </div>
    )
}

export default Membership