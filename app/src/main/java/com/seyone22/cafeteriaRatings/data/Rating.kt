package com.seyone22.cafeteriaRatings.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.material.icons.outlined.SentimentNeutral
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material.icons.outlined.SentimentVeryDissatisfied
import androidx.compose.material.icons.outlined.SentimentVerySatisfied
import androidx.compose.ui.graphics.vector.ImageVector

interface Rating {
    val name: String
    val value: Int
    val icon: ImageVector
    val emoji : String
}

// Unused, Using a 3 point system
object VeryDissatisfied: Rating {
    override val name = "Very Dissatisfied"
    override val value = 0
    override val icon = Icons.Outlined.SentimentVeryDissatisfied
    override val emoji = "\uD83D\uDE22"
}

object Dissatisfied: Rating {
    override val name = "Dissatisfied"
    override val value = 1
    override val icon = Icons.Outlined.SentimentDissatisfied
    override val emoji = "\uD83D\uDE1E"
}

object Neutral: Rating {
    override val name = "Neutral"
    override val value = 2
    override val icon = Icons.Outlined.SentimentNeutral
    override val emoji = "\uD83D\uDE10"
}

object Satisfied: Rating {
    override val name = "Satisfied"
    override val value = 3
    override val icon = Icons.Outlined.SentimentSatisfied
    override val emoji = "\uD83D\uDE0A"
}


// Unused, Using a 3 point system
object VerySatisfied: Rating {
    override val name = "Very Dissatisfied"
    override val value = 5
    override val icon = Icons.Outlined.SentimentVerySatisfied
    override val emoji = "\uD83D\uDE04"
}   