CREATE INDEX idx_stats_days_filter ON stats(created_at);
CREATE INDEX idx_stats_queue_user_id ON stat_queue(user_id);