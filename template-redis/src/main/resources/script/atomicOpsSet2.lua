local res = redis.call('get', KEYS[1])
if not res or tonumber(res) < tonumber(ARGV[1]) then
    redis.call('setex', KEYS[1], ARGV[3], ARGV[1])
    redis.call('setex', KEYS[2], ARGV[3], ARGV[2])
    return 1
else
    return 0
end