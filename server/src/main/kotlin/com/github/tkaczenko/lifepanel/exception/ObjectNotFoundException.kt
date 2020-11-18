package com.github.tkaczenko.lifepanel.exception

/**
 * @author Andrii Tkachenko
 */
class ObjectNotFoundException : IllegalArgumentException {
    constructor(aClass: Class<*>, id: Any?) : super(String.format("%s id %s not found", aClass.name, id))
    constructor(aClass: Class<*>, idName: String?, idValue: Any?) : super(String.format("%s %s %s not found", aClass.name, idName, idValue))
}
