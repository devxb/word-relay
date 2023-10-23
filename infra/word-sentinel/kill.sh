ps -ef | \
grep redis | \
awk '{print $2}' | \
xargs kill
