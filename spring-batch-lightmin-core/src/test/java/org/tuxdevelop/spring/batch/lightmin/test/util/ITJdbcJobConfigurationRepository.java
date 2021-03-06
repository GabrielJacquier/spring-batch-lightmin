package org.tuxdevelop.spring.batch.lightmin.test.util;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.tuxdevelop.spring.batch.lightmin.admin.repository.JdbcJobConfigurationRepository;
import org.tuxdevelop.spring.batch.lightmin.configuration.SpringBatchLightminConfigurationProperties;

public class ITJdbcJobConfigurationRepository extends JdbcJobConfigurationRepository implements ITJobConfigurationRepository {

    private static final String DELETE_FROM_JOB_PARAMETERS =
            "DELETE FROM %s WHERE id >= 0";
    private static final String DELETE_FROM_JOB_VALUES =
            "DELETE FROM %s WHERE id >= 0";
    private static final String DELETE_FROM_JOB_CONFIGURATION =
            "DELETE FROM %s WHERE job_configuration_id >= 0";


    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;
    private final SpringBatchLightminConfigurationProperties springBatchLightminConfigurationProperties;

    public ITJdbcJobConfigurationRepository(final JdbcTemplate jdbcTemplate, final String tablePrefix,
                                            final PlatformTransactionManager platformTransactionManager,
                                            final SpringBatchLightminConfigurationProperties springBatchLightminConfigurationProperties) {
        super(jdbcTemplate, springBatchLightminConfigurationProperties);
        this.jdbcTemplate = jdbcTemplate;

        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
        this.springBatchLightminConfigurationProperties = springBatchLightminConfigurationProperties;
        this.transactionTemplate.setReadOnly(Boolean.FALSE);
    }

    @Override
    public void clean(final String applicationName) {
        this.transactionTemplate.execute(status -> {
            this.jdbcTemplate.update(attachTablePrefix(DELETE_FROM_JOB_PARAMETERS,
                    this.springBatchLightminConfigurationProperties.getJobConfigurationParameterTableName()));
            this.jdbcTemplate.update(attachTablePrefix(DELETE_FROM_JOB_VALUES,
                    this.springBatchLightminConfigurationProperties.getJobConfigurationValueTableName()));
            this.jdbcTemplate.update(attachTablePrefix(DELETE_FROM_JOB_CONFIGURATION,
                    this.springBatchLightminConfigurationProperties.getJobConfigurationTableName()));
            return 1;
        });
    }

    private String attachTablePrefix(final String query, final String tablePrefix) {
        return String.format(query, tablePrefix);
    }
}
