package com.example.chatbot
import android.widget.AbsListView.SelectionBoundsAdjuster
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatbot.ui.theme.ColorModelMessage
import com.example.chatbot.ui.theme.ColorUserMessage
import com.example.chatbot.ui.theme.ColorUserText
import com.example.chatbot.ui.theme.ColorModelText


//import np.com.bimalkafle.ChatBot.ui.theme.ColorModelMessage
//import np.com.bimalkafle.ChatBot.ui.theme.ColorUserMessage
//import np.com.bimalkafle.ChatBot.ui.theme.Purple80


@Composable
fun ChatPage(modifier: Modifier = Modifier,viewModel: ChatViewModel) {
     Column(
         modifier = modifier
             .fillMaxSize()
             .background(Color(0xFFF0EAD6))

     ){
         AppHeader()
         MessageList(modifier = Modifier.weight(1f),
             messageList = viewModel.messageList)
         MessageInput(
             onMessageSend = {
                viewModel.sendMessage(it)
         })
     }
}

@Composable
fun MessageList(modifier: Modifier = Modifier,messageList: List<MessageModel>) {

    if(messageList.isEmpty()){
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.baseline_question_answer_24),
                contentDescription = "Icon",

                )
                Text(text = "Ask me anything" , fontSize = 22.sp)
        }
    }else{
        LazyColumn(modifier = modifier,
            reverseLayout = true
        ){

            items(messageList.reversed()){
                MessageRow(messageModel = it)
            }
        }
    }

}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role=="model"

    Row(verticalAlignment = Alignment.CenterVertically){
        Box(modifier = Modifier.fillMaxWidth()){

            Box(
                modifier = Modifier
                    .align(if(isModel) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(
                        start = if(isModel) 8.dp else 70.dp,
                        end = if(isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(if(isModel) ColorModelMessage else ColorUserMessage)
                    .padding(16.dp)
            ){
                SelectionContainer {
                    Text(
                        text = messageModel.message,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.W500,
                        color = if(isModel) ColorModelText else ColorUserText
                    )
                }

            }

        }

    }
}

@Composable
fun MessageInput(onMessageSend : (String)->Unit) {

    var message by remember{
        mutableStateOf("")
    }

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedTextField(

            value = message,
            onValueChange = {
            message = it
        },
            textStyle = TextStyle(
                fontSize = 19.sp,

            ),
            colors = TextFieldDefaults.colors(

                focusedIndicatorColor = Color(0xFF797979),
                unfocusedIndicatorColor = Color(0xFF4D4D4D),
                disabledContainerColor = Color(0xFFF0EAD6),
                unfocusedContainerColor = Color(0xFFF0EAD6),
                focusedContainerColor = Color(0xFFF0EAD6)

            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            modifier = Modifier
                .weight(1f)

        )
        IconButton(onClick = {
            if(message.isNotEmpty()){
                onMessageSend(message)
                message = ""
            }

        }) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send")
        }
    }
}

@Composable
fun AppHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF705834))
    ){
        Text(modifier = Modifier.padding(16.dp),
            text = "BotBuddy",
            color = Color(0xFFE6E0CC),
            fontSize = 27.sp
        )
    }
}