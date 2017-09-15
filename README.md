# AutoContext
Initialize statically any android class with application Context, by annotation process at compilation


By adding @NeedContext annotation to any public static method, this method will be called after application context is init.

This can be usefull to init any library which need to access Android application context, not Activity's context.
This context can be statically saved in library, without creating any leaks; again it's application context.
