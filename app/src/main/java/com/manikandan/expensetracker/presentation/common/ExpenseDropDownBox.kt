package com.manikandan.expensetracker.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manikandan.expensetracker.domain.model.ExpenseDropDownData
import com.manikandan.expensetracker.presentation.common.util.ExpenseDropDownType
import com.manikandan.expensetracker.presentation.screens.home.AddUpdateTransactionViewModel
import com.manikandan.expensetracker.ui.theme.*

@ExperimentalAnimationApi
@Composable
fun ExpenseDropDownBox(
    dropDownData: ExpenseDropDownData,
    scrollState: ScrollState,
    valueChange: (String) -> Unit,
    addUpdateTransactionViewModel: AddUpdateTransactionViewModel = hiltViewModel()
) {
    var expandStatus by remember {
        mutableStateOf(false)
    }
    var hintText by remember {
        mutableStateOf(dropDownData.hint)
    }
    val isCategoryAndModeReadOnly  = when(dropDownData.dropDownType){
        ExpenseDropDownType.CATEGORY -> addUpdateTransactionViewModel.isCategoryReadOnly.value
        ExpenseDropDownType.TRANSACTION_MODE -> addUpdateTransactionViewModel.isTransactionModeReadOnly.value
        else -> true
    }

    var categoryText by remember {
        mutableStateOf(dropDownData.selectedText)
    }
    Column {
        Text(
            modifier = Modifier,
            text = dropDownData.dropDownTitle,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colors.secondaryColor
        )
        if (isCategoryAndModeReadOnly) {
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = if (expandStatus) MaterialTheme.colors.titleColor else MediumGray,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(
                        vertical = 5.dp,
                        horizontal = 10.dp
                    )
                    .focusable(enabled = true)
                    .clickable {
                        expandStatus = !expandStatus
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = dropDownData.selectedText.ifEmpty { dropDownData.hint },
                        color = if (dropDownData.selectedText.isBlank()) MediumGray else MaterialTheme.colors.titleColor
                    )
                    IconButton(onClick = {
                        expandStatus = !expandStatus
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Arrow Dropdown",
                            tint = MaterialTheme.colors.titleColor
                        )
                    }
                }
            }
        } else {
            OutlinedTextField(
                value = categoryText,
                onValueChange = { category: String ->
                    categoryText = category
                    valueChange(category)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(SMALL_PADDING),
                singleLine = true,
                maxLines = 1,
                trailingIcon = {
                    IconButton(onClick = {
                        expandStatus = !expandStatus
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Arrow Dropdown",
                            tint = MaterialTheme.colors.titleColor
                        )
                    }
                },
                placeholder = {
                    Text(
                        text = hintText,
                        style = MaterialTheme.typography.body1.copy(
                            color = MediumGray
                        )
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = MediumGray,
                    focusedBorderColor = MaterialTheme.colors.textFieldFocusedBorderColor,
                    textColor = MaterialTheme.colors.titleColor,
                    cursorColor = MaterialTheme.colors.titleColor
                )

            )
        }

        AnimatedVisibility(visible = expandStatus) {
            Card(
                modifier = Modifier
                    .padding(top = 1.dp),
                elevation = 6.dp,
                shape = RoundedCornerShape(10.dp),
                backgroundColor = MaterialTheme.colors.dropDownBackground
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeightIn(
                            max = 250.dp
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .verticalScroll(scrollState)
                ) {
                    dropDownData.dropDownList.forEach { dropDownValue ->
                        DropdownMenuItem(onClick = {
                            expandStatus = false
                            if (dropDownValue == "Other") {
                                categoryText = ""
                                hintText = "Type a ${dropDownData.dropDownTitle}"
                                addUpdateTransactionViewModel.updateCategoryAndModeReadMode(
                                    dropDownType = dropDownData.dropDownType,
                                    isReadOnly = false
                                )
                            } else {
                                addUpdateTransactionViewModel.updateCategoryAndModeReadMode(
                                    dropDownType = dropDownData.dropDownType,
                                    isReadOnly = true
                                )
                                valueChange(dropDownValue)
                            }
                        }) {
                            Text(
                                text = dropDownValue,
                                style = MaterialTheme.typography.body1
                            )
                        }

                    }
                }
            }
        }
    }
}

/*
@ExperimentalAnimationApi
@Composable
fun ExpenseDropDownBox(
    dropDownList: List<String>,
    title: String,
    textValue: String,
    hint: String,
    scrollState: ScrollState,
    valueChange: (String) -> Unit
) {
    var expandStatus by remember {
        mutableStateOf(false)
    }
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
                    color = if (expandStatus) MaterialTheme.colors.titleColor else MediumGray,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(
                    vertical = 5.dp,
                    horizontal = 10.dp
                )
                .focusable(enabled = true)
                .clickable {
                    expandStatus = !expandStatus
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = textValue.ifEmpty { hint },
                    color = if (textValue.isBlank()) MediumGray else MaterialTheme.colors.titleColor
                )
                IconButton(onClick = {
                    expandStatus = !expandStatus
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Arrow Dropdown",
                        tint = MaterialTheme.colors.titleColor
                    )
                }
            }
        }

        AnimatedVisibility(visible = expandStatus) {
            Card(
                modifier = Modifier
                    .padding(top = 1.dp),
                elevation = 6.dp,
                shape = RoundedCornerShape(10.dp),
                backgroundColor = MaterialTheme.colors.dropDownBackground
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeightIn(
                            max = 250.dp
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .verticalScroll(scrollState)
                ) {
                    dropDownList.forEach { dropDownValue ->
                        DropdownMenuItem(onClick = {
                            valueChange(dropDownValue)
                            expandStatus = false
                        }) {
                            Text(
                                text = dropDownValue,
                                style = MaterialTheme.typography.body1
                            )
                        }

                    }
                }
            }
        }
    }

}
 */