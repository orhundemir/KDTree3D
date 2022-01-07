public class Driver {
    public static void main(String[] args) {
        
        KDTree3D tree = new KDTree3D();
        Point3D root = new Point3D(50,40,20);
        Point3D el1 = new Point3D(30,25,35);
        Point3D el2 = new Point3D(20,10,5);
        Point3D el3 = new Point3D(80,15,45);
        Point3D el4 = new Point3D(12,22,72);
        Point3D el5 = new Point3D(90,5,30);
        Point3D el6 = new Point3D(95,10,55);
        
        tree.insert(root);
        tree.insert(el1);
        tree.insert(el2);
        tree.insert(el3);
        tree.insert(el4);
        tree.insert(el5);
        tree.insert(el6);

        tree.displayTree();
        System.out.println("****************************");

        System.out.println(tree.findMin(2));
        System.out.println("****************************");

        System.out.println(tree.findMax(0));
        System.out.println("****************************");

        tree.printRange(new Point3D(20,4,4), new Point3D(60,60,60));
        System.out.println("****************************");

        tree.remove(root);
        tree.displayTree();
        System.out.println("****************************");

        tree.remove(new Point3D(95,10,55));
        tree.displayTree();
        System.out.println("****************************");



        System.out.println("****************************\n");
        System.out.println("\nEkstra kontroller:");


        

        tree.insert(new Point3D(80,15,45)); //degisiklik olmamali
        tree.displayTree();
        System.out.println("****************************");

        tree.remove(new Point3D(0,0,0)); // degisiklik olmamali
        tree.displayTree();
        System.out.println("****************************");
        
        Point3D point=tree.search(new Point3D(80,15,45)); // search metodu kontrolu
        System.out.println(point);
        System.out.println("****************************");


        
        
   } 
}
