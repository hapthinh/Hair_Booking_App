package com.example.barbershopapp.Screen.User


import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.barbershopapp.R
import com.example.barbershopapp.ui.theme.fontFamily1
import com.example.barbershopapp.ui.theme.fontFamily2
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerSection(pagerItems: List<PagerItem>, pagerState: PagerState) {
    HorizontalPager(
        state = pagerState,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

    ) { currentPage ->
        val pagerItem = pagerItems[currentPage]

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = pagerItem.imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .requiredSize(500.dp)
                    .fillMaxSize()

            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = pagerItem.textResId),
                fontFamily = fontFamily1,
                style = MaterialTheme.typography.displayLarge,
                fontSize = 28.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = pagerItem.textBodyResId),
                fontFamily = fontFamily2,
                fontSize = 22.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

data class PagerItem(
    val imageResId: Int,
    @StringRes val textResId: Int,
    @StringRes val textBodyResId: Int
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val pagerItems = listOf(
        PagerItem(R.drawable.p1, R.string.tittle_slider_1, R.string.text_body_1),
        PagerItem(R.drawable.p2, R.string.tittle_slider_2, R.string.text_body_2),
        PagerItem(R.drawable.p3, R.string.tittle_slider_3, R.string.text_body_3)
    )

    val pagerState = rememberPagerState(
        pageCount = { pagerItems.size }
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPagerSection(pagerItems, pagerState)
        PageIndicator(
            pageCount = pagerItems.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        ActionButtons(
            onRegisterClick = onRegisterClick,
            onLoginClick = onLoginClick
        )
    }
}

@Composable
fun ActionButtons(onRegisterClick: () -> Unit, onLoginClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onRegisterClick) {
            Text(text = "Đăng ký", fontSize = 26.sp)
        }
        Button(onClick = onLoginClick) {
            Text(text = "Đăng nhập", fontSize = 25.sp)
        }
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage, modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(
        modifier = modifier
            .size(size.value)
            .clip(CircleShape)
            .background(if (isSelected) Color(0xff373737) else Color(0xA8373737))
    )
}
