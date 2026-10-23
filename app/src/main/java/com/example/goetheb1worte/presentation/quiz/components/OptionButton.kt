package com.example.goetheb1worte.presentation.quiz.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun OptionButton(
    optionText: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    isSubmitted: Boolean,
    onClick: () -> Unit
) {
    val containerColor by animateColorAsState(
        targetValue = when {
            isSubmitted && isCorrect -> Color(0xFFD0F0C0)
            isSubmitted && isSelected && !isCorrect -> Color(0xFFFFCDD2)
            else -> MaterialTheme.colorScheme.surface
        },
        label = "OptionColorAnimation"
    )

    val contentColor = when {
        isSubmitted && isCorrect -> Color(0xFF1B5E20)
        isSubmitted && isSelected && !isCorrect -> Color(0xFFB71C1C)
        else -> MaterialTheme.colorScheme.onSurface
    }

    val borderColor = when {
        isSubmitted && isCorrect -> Color(0xFF2E7D32)
        isSubmitted && isSelected && !isCorrect -> Color(0xFFD32F2F)
        else -> MaterialTheme.colorScheme.outlineVariant
    }

    OutlinedButton(
        onClick = onClick,
        enabled = !isSubmitted,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor,
            disabledContentColor = contentColor
        ),
        border = BorderStroke(if (isSubmitted && (isCorrect || isSelected)) 2.dp else 1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = optionText,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isSubmitted && isCorrect) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier.weight(1f),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            if (isSubmitted && isCorrect) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Correct",
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(22.dp)
                )
            } else if (isSubmitted && isSelected && !isCorrect) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Wrong",
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}
