package com.manikandan.expensetracker.presentation.screens.home.components.add_transaction

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manikandan.expensetracker.R
import com.manikandan.expensetracker.ui.theme.MediumGray
import com.manikandan.expensetracker.ui.theme.secondaryColor
import com.manikandan.expensetracker.ui.theme.titleColor
import java.util.*

@Composable
fun CustomDatePickerView(
    title: String,
    pickedDate: String,
    onDateChanged: (date: Long?) -> Unit
) {
    val context = LocalContext.current
    Column {
        Text(
            modifier = Modifier,
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondaryColor
        )
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MediumGray,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(
                    vertical = 5.dp,
                    horizontal = 10.dp
                )
                .clickable {
                    showDatePicker(
                        context = context,
                        onDateChanged = onDateChanged
                    )
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pickedDate.ifEmpty { "Pick a date"},
                    color = if (pickedDate.isEmpty()) MediumGray else MaterialTheme.colors.titleColor
                )
                IconButton(onClick = {
                    showDatePicker(
                        context = context,
                        onDateChanged = onDateChanged
                    )
                }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(R.string.date_range_icon),
                        tint = MaterialTheme.colors.titleColor
                    )
                }
            }
        }
    }
}

private fun showDatePicker(
    context: Context,
    onDateChanged: (date: Long?) -> Unit
){
    val year: Int
    val month: Int
    val dayOfMonth: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePicker = DatePickerDialog(
        context,
        {_: DatePicker, selectedYear:Int, selectedMonth:Int, selectedDayOfMonth:Int ->
            calendar.set(selectedYear, selectedMonth, selectedDayOfMonth)
            onDateChanged(calendar.timeInMillis)
        }, year, month, dayOfMonth

    )

    //Setting the maximum date to current date.
    datePicker.datePicker.maxDate = calendar.timeInMillis

    datePicker.show()
}