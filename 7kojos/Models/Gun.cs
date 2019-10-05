using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace _7kojos.Models
{
    public class Gun
    {
        public int id { get; set; }
        public int type { get; set; } // 0 - lankas, 1 - sautuvas
        public int dmg { get; set; }
        public int dmgHead { get; set; }
    }
}
