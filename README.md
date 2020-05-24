# Macro module Yaku

Actions added by the module:
- `mod(%#divident%,%#divisor%);` - does same as `mod`, or `%`, operator in other programming laguages. Doesn't write the result to any variable, just returns it
- `trunc(%#float.number%);` - truncates fractional digits and returns the integer part of the number
- `ackermann(%#m%,%#n%);` - Ackermann function implementation
- `calcstacks(%#items%,#stacks,#leftovers);` - calculates stacks
- `pickmod(%&[namespace:]itemid[:damage]%...,%addInCreative%);` - improved original `pick` action. Supports IDs with mod's namespace and have extra parameter. Set it to true so the action will add specified item to hotbar in creative mod if you don't have it
- `antighost;` - useful for servers to resolve ghost blocks issue. Sends digging packet in a cube 9x9 around the player
