package io.freddie.batchstudy

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@EnableBatchProcessing
@SpringBootApplication
class BatchStudyApplication(
    private val jobLauncher: JobLauncher,
    private val printJob: Job
) {

//    @Bean
//    fun runner() = ApplicationRunner {
//        jobLauncher.run(
//            printJob, JobParametersBuilder()
//                .toJobParameters()
//        )
//    }
}

fun main(args: Array<String>) {
    runApplication<BatchStudyApplication>(*args)
}
