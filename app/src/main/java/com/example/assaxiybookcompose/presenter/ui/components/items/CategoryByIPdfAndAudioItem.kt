package com.example.assaxiybookcompose.presenter.ui.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.assaxiybookcompose.R
import com.example.assaxiybookcompose.data.model.BookUIData
import com.example.assaxiybookcompose.presenter.ui.theme.AppBgColorSecondary
import com.example.assaxiybookcompose.presenter.ui.theme.SecondaryTextColor
import com.example.assaxiybookcompose.presenter.ui.theme.TypeColor
import com.example.assaxiybookcompose.presenter.ui.theme.TypeColorBg

enum class TypeEnum {
    PDF, AUDIO
}

@Composable
fun CategoryByIPdfAndAudioItem(
    modifier: Modifier = Modifier,
    bookUIData: BookUIData,
    onClickItem: (BookUIData) -> Unit = {},
    typeEnum: TypeEnum
) {
    Box(
        modifier = Modifier
            .padding(vertical = 7.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10))
            .background(color = AppBgColorSecondary)
            .clickable {
                onClickItem.invoke(bookUIData)
            }
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(100.dp)
                    .height(130.dp)
                    .clip(RoundedCornerShape(10)),
                model = bookUIData.coverImage,
                contentDescription = "",
                error = painterResource(id = R.drawable.ic_logo_1),
                placeholder = painterResource(id = R.drawable.book),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 15.dp)
                    .height(130.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(alignment = Alignment.TopStart)
                ) {
                    Text(
                        text = bookUIData.name,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 2.dp),
                        text = bookUIData.author,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = SecondaryTextColor,
                        fontSize = 15.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.BottomStart)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .align(Alignment.BottomStart)
                    ) {
                        for (i in 0..4) {
                            Image(
                                modifier = Modifier
                                    .padding(end = 6.dp)
                                    .width(16.dp)
                                    .height(16.dp),
                                painter = painterResource(id = R.drawable.ic_star_svgrepo_com),
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(
                                    color = if (i == 4) SecondaryTextColor else TypeColor
                                )
                            )
                        }
                    }
                    Image(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .width(35.dp)
                            .height(35.dp)
                            .background(color = TypeColorBg)
                            .padding(all = 8.dp),
                        painter = painterResource(
                            id = if (typeEnum == TypeEnum.AUDIO) {
                                R.drawable.ic_menu_audio
                            } else {
                                R.drawable.ic_menu_book
                            }
                        ),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(color = TypeColor)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCategoryByIPdfAndAudioItem() {
    CategoryByIPdfAndAudioItem(
        bookUIData = BookUIData(),
        typeEnum = TypeEnum.AUDIO
    )
}