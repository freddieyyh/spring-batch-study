package io.freddie.batchstudy.configuration

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BatchConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    fun printJob(): Job {
        return jobBuilderFactory.get("print-job")
            .start(alphabetPrintStep())
            .build()
    }

    @Bean
    fun alphabetPrintStep(): Step {
        val chuckSize = 3
        return stepBuilderFactory.get("alphabet-print-step")
            .chunk<String, String>(chuckSize)
            .reader(alphabetReader())
            .writer(printItemWriter())
            .build()
    }

    fun alphabetReader() = ListItemReader(listOf("A", "B", "C", "D", "E", "F"))

    fun printItemWriter() = ItemWriter<String> { list ->
        println(list.joinToString())
    }
}