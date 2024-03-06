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
}

object VeryDissatisfied: Rating {
    override val name = "Very Dissatisfied"
    override val value = 0
    override val icon = Icons.Outlined.SentimentVeryDissatisfied
}

object Dissatisfied: Rating {
    override val name = "Dissatisfied"
    override val value = 1
    override val icon = Icons.Outlined.SentimentDissatisfied
}

object Neutral: Rating {
    override val name = "Neutral"
    override val value = 3
    override val icon = Icons.Outlined.SentimentNeutral
}

object Satisfied: Rating {
    override val name = "Satisfied"
    override val value = 4
    override val icon = Icons.Outlined.SentimentSatisfied
}

object VerySatisfied: Rating {
    override val name = "Very Dissatisfied"
    override val value = 5
    override val icon = Icons.Outlined.SentimentVerySatisfied
}