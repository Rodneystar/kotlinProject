package kotlinProject

import org.junit.jupiter.api.DisplayNameGenerator
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.springframework.beans.ConfigurablePropertyAccessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.env.OriginTrackedMapPropertySource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.runner.WebApplicationContextRunner
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.core.env.*
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.AbstractMap
import kotlin.test.Test


@SpringBootTest
@ExtendWith(SpringExtension::class)
class AppKtTest
@Autowired constructor (
        var env: StandardEnvironment,
        var ctx : ApplicationContext
){
    @Test
    fun contextLoads() {
        println("name is ${ctx.beanDefinitionCount}")
        println("heres things")
        println(ctx.environment.get("jonnysname"))
        var env = ctx.environment
        if(env is ConfigurableEnvironment) {
            env.propertySources
                    .filterIsInstance<MapPropertySource>()
                    .filter{ isNotSystemPropertiesOrEnv(it.name) }
                    .flatMap{ pSource -> pSource.propertyNames.map{simpleEntry("${pSource.name}, $it", env.getProperty(it)) }}
                    .forEach{println( "${it.key} : ${it.value}")}
        }
    }

    @Test
    fun environmentBean() {
        env.propertySources
                .filterIsInstance<MapPropertySource>()
                .filter{ isNotSystemPropertiesOrEnv(it.name) }
                .flatMap{ pSource -> pSource.propertyNames.map{simpleEntry("${pSource.name}, $it", env.getProperty(it)) }}
                .forEach{println( "${it.key} : ${it.value}")}
    }

}
    fun isNotSystemPropertiesOrEnv(name: String) : Boolean {
        return !name.equals("systemProperties") && !name.equals("systemEnvironment")
    }
    fun <K, V> simpleEntry(key: K, value: V) : AbstractMap.SimpleEntry<K,V> {
        return AbstractMap.SimpleEntry(key, value)
    }