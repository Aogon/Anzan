package app.sakai.tororoimo.anzan

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Question(
    @PrimaryKey
    open var id : Int? = null,
    open var factor1: Int = 0,
    open var factor2: Int = 0
) : RealmObject() {
}