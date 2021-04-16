package io.freddie.batchstudy.listener

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport

class ElapsedTimeListener : JobExecutionListenerSupport() {
    override fun beforeJob(jobExecution: JobExecution) {
        println(System.currentTimeMillis())
    }

    override fun afterJob(jobExecution: JobExecution) {
        println(System.currentTimeMillis())
    }
}