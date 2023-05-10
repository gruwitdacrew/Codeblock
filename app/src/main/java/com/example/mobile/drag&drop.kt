//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.toMutableStateList
//import com.example.mobile.blocksToRender
//
//fun checkForOverScroll(): Float {
//    return initiallyDraggedElement?.let {
//        val startOffset = it.offset + draggedDistance
//        val endOffset = it.offsetEnd + draggedDistance
//        val viewPortStart = lazyListState.layoutInfo.viewportStartOffset
//        val viewPortEnd = lazyListState.layoutInfo.viewportEndOffset
//
//        when {
//            draggedDistance > 0 -> (endOffset - viewPortEnd).takeIf { diff -> diff > 0 }
//            draggedDistance < 0 -> (startOffset - viewPortStart).takeIf { diff -> diff < 0 }
//            else -> null
//        }
//    } ?: 0f
//}
//onDrag = { offset ->
//    ..
//    initiallyDraggedElement?.let {
//        val startOffset = it.offset + draggedDistance
//        val endOffset = it.offsetEnd + draggedDistance
//
//        currentElementItemInfo?.let { hovered ->
//
//            lazyListState.layoutInfo.visibleItemsInfo
//                .filterNot { item ->
//                    item.offsetEnd < startOffset || item.offset > endOffset
//                }
//        }
//    }
//}
//onDragEnd = {
//    draggedDistance = 0f
//    currentIndexOfDraggedItem = null
//    initiallyDraggedElement = null
//    overscrollJob?.cancel()
//},
//onDragCancel = {
//    draggedDistance = 0f
//    currentIndexOfDraggedItem = null
//    initiallyDraggedElement = null
//    overscrollJob?.cancel()
//}
//@Composable
//fun ReorderList(..) {
//    val lazyListState = rememberLazyListState()
//
//    // used to obtain initial offsets on drag start
//    var initiallyDraggedElement by remember {mutableStateOf<LazyListItemInfo?>(null) }
//
//    var currentIndexOfDraggedItem by remember { mutableStateOf<Int?>(null) }
//
//    LazyColumn(
//        modifier = ..
//    detectDragGesturesAfterLongPress(
//        onDragStart = { offset ->
//            state.layoutInfo.visibleItemsInfo
//                .firstOrNull { item -> offset.y.toInt() in item.offset..item.offsetEnd }
//                ?.also {
//                    currentIndexOfDraggedItem = it.index
//                    initiallyDraggedElement = it
//                }
//        },
//    )
//},
//state = lazyListState
//)
//}
//var elementDisplacement by remember { mutableStateOf(0f) }
//
//var currentIndexOfDraggedItem by remember { .. }
//
//LazyColumn(
//modifier = ..
//detectDragGesturesAfterLongPress(
//onDrag = { change, offset ->
//    change.consumeAllChanges()
//    draggedDistance += offset.y
//}
//),
//) {
//    itemsIndexed(items) { index, item ->
//        Box(modifier = Modifier
//            .graphicsLayer {
//                // only move the element if that is where Drag started
//                translationY = draggedDistance
//                    .takeIf { index == currentIndexOfDraggedItem } ?: 0f
//            }
//        )
//    }
//}
//onDrag = { offset ->
//    ..
//    currentElementItemInfo?.let { hovered ->
//
//        lazyListState.layoutInfo.visibleItemsInfo
//            .filterNot {..}
//            .firstOrNull { item ->
//                val delta = startOffset - hovered.offset
//                when {
//                    delta > 0 -> (endOffset > item.offsetEnd)
//                    else -> (startOffset < item.offset)
//                }
//            }
//    }
//}
//@Composable
//fun ReorderableList(models: List<T>) {
//
//    val lazyListState = rememberLazyListState()
//
//    val calculatedOffset = remember { mutableStateOf<Float>() }
//
//    LazyColumn(
//        modifier = ..
//        .pointerInput(Unit) {
//            detectDragGesturesAfterLongPress(
//                onDrag = { change, offset ->
//                    change.consumeAllChanges()
//                    // compute calculatedOffset
//                    ..
//                },
//                onDragStart = { offset -> .. },
//                onDragEnd = { .. },
//                onDragCancel = { .. }
//            )
//        },
//    state = lazyListState
//    ) {
//        items(models) {
//            Box(modifier = Modifier
//                .graphicsLayer(translationY = calculatedOffset ?: 0f)
//            )
//        }
//    }
//}
//// since LazyListState.scrollBy() is a suspend function
//val scope = rememberCoroutineScope()
//
//var overscrollJob by remember { mutableStateOf<Job?>(null) }
//
//LazyColumn(
//modifier = ..
//detectDragGesturesAfterLongPress(
//onDrag = {
//    ..
//
//    // let current LazyListState.scrollBy() not be interrupted
//    if (overscrollJob?.isActive == true)
//        return
//
//    // launch LazyListState.scrollBy only if overscrolled offset != 0
//    checkForOverScroll()
//        .takeIf { offset -> offset != 0f }
//        ?.let { offset -> overscrollJob = scope.launch { lazyListState.scrollBy(offset) } }
//        ?: run { overscrollJob?.cancel() }
//}
//)
//)
//@Composable
//fun ReorderList(
//    items: List<T>,
//    onMove: (fromIndex: Int, toIndex: Int) -> Unit
//) {
//    ..
//    onDrag = {
//        ..
//        currentElementItemInfo?.let { hovered ->
//
//            lazyListState.layoutInfo.visibleItemsInfo
//                .filterNot {..}
//                .firstOrNull {..}
//                ?.also { item ->
//                    currentIndexOfDraggedItem?.let { current ->
//                        onMove.invoke(current, item.index)
//                    }
//                    currentIndexOfDraggedItem = item.index
//                }
//        }
//    }
//}
//
//@Composable
//fun Screen() {
//    val list = listOf(blocksToRender).toMutableStateList()
//
//    ReorderList(
//        items = list,
//        onMove = { fromIndex, toIndex -> list.move(fromIndex, toIndex) }
//    )
//}