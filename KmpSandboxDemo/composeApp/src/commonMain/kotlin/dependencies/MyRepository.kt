package dependencies

interface MyRepository {
    fun hello(): String
}

class MyRepositoryImpl(
    private val dbClient: DbClient
): MyRepository {
    override fun hello(): String {
        return "Hello, I am the repository!"
    }

}