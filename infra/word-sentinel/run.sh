rm -rf ./copy/* && \
cp server/* ./copy && \
cp sentinel/* ./copy && \
redis-server copy/redis_6380.conf && \
redis-server copy/redis_6381.conf && \
redis-server copy/redis_6382.conf && \
redis-sentinel copy/redis-sentinel_26379.conf && \
redis-sentinel copy/redis-sentinel_26380.conf && \
redis-sentinel copy/redis-sentinel_26381.conf
