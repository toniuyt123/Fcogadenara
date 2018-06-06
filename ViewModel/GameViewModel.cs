using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using GamesSQL.Model;
using System.Collections.ObjectModel;   

namespace GamesSQL.ViewModel
{
    public class GameViewModel
    {
        public GameViewModel() {
            LoadGames();    
        }

        public ObservableCollection<Game> Games
        {
            get;
            set;
        }

        public void LoadGames()
        {
            ObservableCollection<Game> games = new ObservableCollection<Game>();

            games.Add(new Game { Title = "Fortnite"});
            games.Add(new Game { Title = "LigaLEgents"});

            Games = games;
        }
    }
}
