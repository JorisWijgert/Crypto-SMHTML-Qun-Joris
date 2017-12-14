namespace SimpleMeal.Models
{
    using System;
    using System.Data.Entity;
    using System.Linq;

    public class SimpleMealModel : DbContext
    {
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Recipe>().HasMany(u => u.RecipeProducts).WithMany();
            modelBuilder.Entity<Supermarket>().HasMany(u => u.SupermarketProduct).WithMany();
            modelBuilder.Entity<Profile>().HasMany(u => u.History).WithMany();
        }

        // Your context has been configured to use a 'SimpleMealModel' connection string from your application's 
        // configuration file (App.config or Web.config). By default, this connection string targets the 
        // 'SimpleMeal.Models.SimpleMealModel' database on your LocalDb instance. 
        // 
        // If you wish to target a different database and/or database provider, modify the 'SimpleMealModel' 
        // connection string in the application configuration file.
        public SimpleMealModel()
            : base("name=SimpleMealModel")
        {
        }

        public DbSet<Recipe> Recipe { get; set; }
        public DbSet<Product> Product { get; set; }
        public DbSet<Supermarket> Supermarket { get; set; }
        public DbSet<Profile> Profile { get; set; }
        // Add a DbSet for each entity type that you want to include in your model. For more information 
        // on configuring and using a Code First model, see http://go.microsoft.com/fwlink/?LinkId=390109.

        // public virtual DbSet<MyEntity> MyEntities { get; set; }
    }

    //public class MyEntity
    //{
    //    public int Id { get; set; }
    //    public string Name { get; set; }
    //}
}