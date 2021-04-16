package io.freddie.batchstudy

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@EnableBatchProcessing
@SpringBootApplication
class BatchStudyApplication(
    private val jobLauncher: JobLauncher,
    private val jobExplorer: JobExplorer,
    private val timeStampJob: Job
) {

    @Bean
    fun runner() = ApplicationRunner {
        jobLauncher.run(
            timeStampJob, JobParametersBuilder(jobExplorer)
                .getNextJobParameters(timeStampJob)
                .toJobParameters()
        )
    }
}

fun main(args: Array<String>) {
    runApplication<BatchStudyApplication>(*args)
}
