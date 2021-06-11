package app.peterkwp.s611.ui.data

import androidx.annotation.IntDef

@Retention(AnnotationRetention.SOURCE)
@IntDef(
    TabType.S611_NEW,
    TabType.S611_BOOKMARK,
    TabType.S611_HISTORY
)
annotation class TabType {
    companion object {
        const val S611_NEW = 0
        const val S611_BOOKMARK = 1
        const val S611_HISTORY = 2
    }
}

@Retention(AnnotationRetention.SOURCE)
@IntDef(
    ItemType.S611_NONE,
    ItemType.S611_REMOVE,
    ItemType.S611_MOVE
)
annotation class ItemType {
    companion object {
        const val S611_NONE = 0
        const val S611_REMOVE = 1
        const val S611_MOVE = 2
    }
}