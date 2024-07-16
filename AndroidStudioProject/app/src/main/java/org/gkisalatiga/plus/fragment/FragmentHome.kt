/**
 * AGPL-3.0-licensed
 * Copyright (C) GKI Salatiga 2024
 * Written by Samarthya Lykamanuella (github.com/groaking)
 */

package org.gkisalatiga.plus.fragment

import android.content.Context
import android.graphics.Paint.Align
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.constraintlayout.solver.widgets.Rectangle
import androidx.core.graphics.drawable.toDrawable
import androidx.navigation.NavHostController
import org.gkisalatiga.plus.R
import org.gkisalatiga.plus.lib.DownloadAndSaveImageTask
import org.gkisalatiga.plus.lib.FileManager

class FragmentHome : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    // The string text used in the main menu items.
    val mainMenuText = listOf (
        "Profil Gereja",
        "Kependetaan",
        "Kemajelisan",
        "Badan Pelayanan"
    )

    // The icons used in the main menu items.
    val mainMenuIcon = listOf (
        R.drawable.baseline_compost_24,
        R.drawable.baseline_compost_24,
        R.drawable.baseline_compost_24,
        R.drawable.baseline_compost_24
    )

    /**
     * Navigation between screens
     * SOURCE: https://medium.com/@husayn.fakher/a-guide-to-navigation-in-jetpack-compose-questions-and-answers-d86b7e6a8523
     */
    @Composable
    public fun getComposable(screenController: NavHostController, fragmentController: NavHostController, context: Context) {
        Column {
            // Setting the layout to center both vertically and horizontally
            // SOURCE: https://codingwithrashid.com/how-to-center-align-ui-elements-in-android-jetpack-compose/
            val scrollState = rememberScrollState()
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState)
            ) {
                /* Displaying the welcome banner in the main menu. */
                Surface (
                    shape = RoundedCornerShape(0.dp, 0.dp, 40.dp, 40.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.sample_welcome_banner),
                        contentDescription = "Some name",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                /* Displaying the "main menu" options. */
                LazyRow {
                    items (4) { index ->
                        Button(onClick = {}, modifier = Modifier.wrapContentWidth(), shape = RoundedCornerShape(10.dp)) {
                            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(mainMenuIcon[index]),
                                    contentDescription = "Some name",
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Text(mainMenuText[index])
                            }
                        }
                    }
                }

                /* Displaying the daily Bible verses. */
                Text("Inspirasi Harian", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Row {
                    Text("Mazmur 203:1-1002", fontWeight = FontWeight.Bold)
                    Text("TB1")
                    Text("Senin, 32 Juli 2024")
                }
                Text("Sungguh alangkah baiknya, sungguh alangkah indahnya, bila saudara semua hidup rukun bersama." +
                        "Seperti minyak di kepala Harun yang ke janggut dan jubahnya turun." +
                        "Seperti embun yang dari Hermon mengalir ke bukit Sion.")

                /* Displaying the daily morning devotion. */
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text("Sapaan dan Renungan Pagi", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    // SOURCE: https://stackoverflow.com/a/69278397
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {}) { Text("Lihat Semua") }
                }
                LazyRow {
                    items (5) {
                        Card (
                            onClick = { Toast.makeText(context, "The card $title is clicked", Toast.LENGTH_SHORT).show() },
                            modifier = Modifier.fillMaxHeight().width(300.dp).padding(top = 10.dp),
                        ) {
                            Column {
                                Image(
                                    painter = painterResource(R.drawable.sample_thumbnail_youtube),
                                    contentDescription = "Some name",
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Text("Title of the menu.", fontWeight = FontWeight.Bold)
                                Text("Description runs here. Lorem Ipsum DOlor Sit Maet. Consecttur adipsicing elit.")
                            }
                        }
                    }
                }

                /* Displaying the choir production videos. */
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text("GKI Salatiga Choir", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    // SOURCE: https://stackoverflow.com/a/69278397
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {}) { Text("Lihat Semua") }
                }
                LazyRow {
                    items (5) {
                        Card (
                            onClick = { Toast.makeText(context, "The card $title is clicked", Toast.LENGTH_SHORT).show() },
                            modifier = Modifier.fillMaxHeight().width(300.dp).padding(top = 10.dp),
                        ) {
                            Column {
                                Image(
                                    painter = painterResource(R.drawable.sample_thumbnail_youtube_2),
                                    contentDescription = "Some name",
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Text("Title of the menu.", fontWeight = FontWeight.Bold)
                                Text("Description runs here. Lorem Ipsum DOlor Sit Maet. Consecttur adipsicing elit.")
                            }
                        }
                    }
                }

                /* Displaying the music videos produced by Komisi Musik dan Liturgi (KML). */
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text("KML Production", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    // SOURCE: https://stackoverflow.com/a/69278397
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {}) { Text("Lihat Semua") }
                }
                LazyRow {
                    items (5) {
                        Card (
                            onClick = { Toast.makeText(context, "The card $title is clicked", Toast.LENGTH_SHORT).show() },
                            modifier = Modifier.fillMaxHeight().width(300.dp).padding(top = 10.dp),
                        ) {
                            Column {
                                Image(
                                    painter = painterResource(R.drawable.sample_thumbnail_youtube_3),
                                    contentDescription = "Some name",
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Text("Title of the menu.", fontWeight = FontWeight.Bold)
                                Text("Description runs here. Lorem Ipsum DOlor Sit Maet. Consecttur adipsicing elit.")
                            }
                        }
                    }
                }
            }
        }
    }
}