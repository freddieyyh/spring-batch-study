package io.freddie.batchstudy.configuration

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TimeStampBatchConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    fun timeStampJob(printTimeStampJob: Step): Job {
        return jobBuilderFactory.get("timestamp-job")
            .incrementer(RunIdIncrementer())
            .start(printTimeStampJob)
            .build()
    }

    @Bean
    fun printTimeStampJob(): Step {
        return stepBuilderFactory.get("print-timestamp-step")
            .tasklet { contribution, chunkContext ->
                println(System.currentTimeMillis())
                RepeatStatus.FINISHED
            }
            .build()
    }
}