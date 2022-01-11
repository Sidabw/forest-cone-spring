local res = redis.call('hmget', KEYS[1], KEYS[2])
if not res[1] or tonumber(res[1]) < tonumber(ARGV[1]) then
  redis.call('hmset', KEYS[1], KEYS[2], ARGV[1])
  redis.call('set', KEYS[3], ARGV[2])
  return 1
else
  return 0
end