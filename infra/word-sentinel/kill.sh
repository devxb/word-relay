sudo ps -ef | \
sudo grep redis | \
sudo awk '{print $2}' | \
sudo xargs kill
