package io.freddie.batchstudy.configuration

import io.freddie.batchstudy.listener.ElapsedTimeListener
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.task.batch.listener.TaskBatchExecutionListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BatchConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val taskBatchExecutionListener: TaskBatchExecutionListener
) {
@Bean
fun printJob(alphabetPrintStep: Step): Job {
    return jobBuilderFactory.get("print-job")
        .start(alphabetPrintStep)
        .listener(taskBatchExecutionListener)
        .listener(elapsedTimeListener())
        .build()
}

    @Bean
    @JobScope
    fun alphabetPrintStep(@Value("#{jobParameters['alphabets']}") alphabets: String): Step {
        val chuckSize = 3
        return stepBuilderFactory.get("alphabet-print-step")
            .chunk<String, String>(chuckSize)
            .reader(alphabetReader(alphabets))
            .processor(duplicateItemProcessor())
            .writer(printItemWriter())
            .build()
    }

    fun alphabetReader(alphabets: String) = ListItemReader(alphabets.split(","))

    fun duplicateItemProcessor() = ItemProcessor<String, String> { string ->
        "$string$string"
    }

    fun printItemWriter() = ItemWriter<String> { list ->
        println(list.joinToString())
    }

    fun elapsedTimeListener() = ElapsedTimeListener()
}