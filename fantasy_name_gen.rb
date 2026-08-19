# fantasy_name_gen.rb
#!/usr/bin/env ruby
require 'optparse'

RACES = {
  elf: {
    male: ['Ae', 'El', 'Ael', 'An', 'Ar', 'Bel', 'Cal', 'Celeb', 'Eär', 'Eld', 'Elr', 'Fin', 'Gil', 'Glor', 'Hald', 'Isil', 'Leg', 'Lind', 'Lúth', 'Maed', 'Mag', 'Mir', 'Nim', 'Oro', 'Rían', 'Saur', 'Sil', 'Tel', 'Thing', 'Tuor', 'Vor', 'Aran', 'Beleg', 'Beren', 'Círd'],
    female: ['Aew', 'An', 'Ar', 'Ared', 'Arwen', 'Cal', 'Cala', 'Celeb', 'El', 'Eär', 'El', 'Elen', 'Elr', 'Eow', 'Eä', 'Findu', 'Galad', 'Gald', 'Gil', 'Glor', 'Idril', 'Lúth', 'Míri', 'Nim', 'Sil', 'Tinú', 'Ung', 'Van', 'Varda', 'Yav', 'Zira']
  },
  orc: {
    male: ['Az', 'Bash', 'Borg', 'Burz', 'Dush', 'Ghâsh', 'Ghol', 'Ghor', 'Goth', 'Grak', 'Grish', 'Grom', 'Gul', 'Karg', 'Lug', 'Maug', 'Mog', 'Mok', 'Nazg', 'Rak', 'Rat', 'Shag', 'Skull', 'Snaga', 'Tark', 'Thrak', 'Ugl', 'Uruk', 'Zog'],
    female: ['Ag', 'As', 'Bagh', 'Borga', 'Dusha', 'Ghrá', 'Ghâsh', 'Ghola', 'Ghora', 'Gotha', 'Grak', 'Grisha', 'Groma', 'Gula', 'Karga', 'Luga', 'Mauga', 'Moga', 'Moka', 'Nazga', 'Raka', 'Rata', 'Shaga', 'Skulla', 'Snaga', 'Tarka', 'Thraka', 'Ugla', 'Uruka', 'Zoga']
  },
  human: {
    male: ['Ad', 'Al', 'Ald', 'Ar', 'Aric', 'Bael', 'Bald', 'Bri', 'Ced', 'Cor', 'Dael', 'Dar', 'Dun', 'Ead', 'Erik', 'Far', 'Gar', 'Grim', 'Hald', 'Har', 'Ing', 'Jar', 'Kael', 'Leif', 'Lor', 'Mar', 'Ned', 'Nor', 'Osw', 'Ragn', 'Ran', 'Rolf', 'Sig', 'Sven', 'Theo', 'Thor', 'Ulf', 'Val', 'Vid', 'Wulf'],
    female: ['Ad', 'Al', 'Alda', 'Ari', 'Asta', 'Bael', 'Bald', 'Bri', 'Ceda', 'Cora', 'Dael', 'Dara', 'Duna', 'Eada', 'Erika', 'Fara', 'Gara', 'Grima', 'Halda', 'Hara', 'Inga', 'Jara', 'Kaela', 'Leifa', 'Lora', 'Mara', 'Neda', 'Nora', 'Os', 'Ragna', 'Rana', 'Rolfa', 'Siga', 'Svena', 'Thea', 'Thora', 'Ulf', 'Vala', 'Vida', 'Wulf']
  },
  dragon: {
    male: ['Alar', 'Aur', 'Bel', 'Brin', 'Chron', 'Drak', 'Ember', 'Faf', 'Flam', 'Glaed', 'Gor', 'Hrath', 'Ign', 'Ith', 'Kael', 'Kor', 'Lor', 'Mal', 'Mor', 'Nad', 'Or', 'Pyros', 'Rath', 'Rogn', 'Saph', 'Sha', 'Smaug', 'Tar', 'Thorn', 'Ulf', 'Val', 'Vir', 'Wyr', 'Xar', 'Zar'],
    female: ['Alara', 'Aura', 'Bela', 'Brina', 'Chrona', 'Draka', 'Embera', 'Fafa', 'Flama', 'Glaeda', 'Gora', 'Hratha', 'Igna', 'Itha', 'Kaela', 'Kora', 'Lora', 'Mala', 'Mora', 'Nada', 'Ora', 'Pyra', 'Ratha', 'Rogna', 'Sapha', 'Shara', 'Smauga', 'Tara', 'Thorna', 'Ulfa', 'Vala', 'Vira', 'Wyra', 'Xara', 'Zara']
  }
}
FALLBACK = ['Ael', 'Bel', 'Cal', 'Dal', 'El', 'Far', 'Gar', 'Hald', 'Ian', 'Jar', 'Kael', 'Lor', 'Mar', 'Nor', 'Or', 'Par', 'Quen', 'Ral', 'Sar', 'Tal', 'Val', 'Wen', 'Xan', 'Yor', 'Zan']

def generate_name(race, gender, min_len, max_len)
  pool = []
  r = race == 'any' ? RACES.keys.sample : race.to_sym
  g = gender == 'any' ? ['male', 'female'].sample : gender.to_sym
  if RACES[r] && RACES[r][g]
    pool = RACES[r][g]
  else
    pool = FALLBACK
  end
  loop do
    num_parts = rand(1..3)
    name = num_parts.times.map { pool.sample }.join
    name = name.capitalize
    return name if name.length.between?(min_len, max_len)
  end
end

options = {}
OptionParser.new do |opts|
  opts.banner = "Usage: fantasy_name_gen.rb [options]"
  opts.on('-n COUNT', '--count COUNT', Integer, 'Number of names') { |v| options[:count] = v }
  opts.on('-g GENDER', '--gender GENDER', ['male', 'female', 'any'], 'Gender') { |v| options[:gender] = v }
  opts.on('-r RACE', '--race RACE', ['elf', 'orc', 'human', 'dragon', 'any'], 'Race') { |v| options[:race] = v }
  opts.on('-o FILE', '--output FILE', 'Output file') { |v| options[:output] = v }
  opts.on('--min N', Integer, 'Minimum length') { |v| options[:min] = v }
  opts.on('--max N', Integer, 'Maximum length') { |v| options[:max] = v }
end.parse!

options[:count] ||= 1
options[:gender] ||= 'any'
options[:race] ||= 'any'
options[:min] ||= 2
options[:max] ||= 12

names = options[:count].times.map { generate_name(options[:race], options[:gender], options[:min], options[:max]) }

if options[:output]
  File.open(options[:output], 'w') { |f| f.puts names }
  puts "✅ Saved #{names.size} names to #{options[:output]}"
else
  puts "\n🧙 Fantasy Name Generator (Race: #{options[:race]}, Gender: #{options[:gender]})"
  names.each_with_index { |name, i| puts "#{i+1}. #{name}" }
end
