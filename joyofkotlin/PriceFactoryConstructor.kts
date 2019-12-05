package joyofkotlin

data class PriceFactoryConstructor private constructor (private val value: Double) {

    override fun toString() = value.toString()

    operator fun plus(price: PriceFactoryConstructor) = PriceFactoryConstructor(this.value + price.value)

    operator fun times(num: Int) = PriceFactoryConstructor(this.value * num)

    companion object {
        val identity = PriceFactoryConstructor(0.0)

        operator fun invoke(value: Double) =
            if (value > 0)
                PriceFactoryConstructor(value)
            else
                throw IllegalArgumentException(
                    "Price must be positive")
    }
}


val newPrice = PriceFactoryConstructor(0.0)
