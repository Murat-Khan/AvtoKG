package com.murat.avtokg.ui.brandfragment

data class  Brand(
    var image: Int? = null,
    var name: String? = null,
    var models : List<String>
        ) : java.io.Serializable