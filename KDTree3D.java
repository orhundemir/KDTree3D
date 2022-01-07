import java.util.ArrayList;
import java.util.List;


/**
 * Concrete implementation of a kd tree using a node-based, linked structure.
 * This class implemented for BIL212(TOBB ETU) class homework in 1.12.2021.
 * 
 * @author Huseyin Orhun Demir
 *
 * 
 */
public class KDTree3D extends LinkedBinaryTree<Point3D> {
    
   
    public KDTree3D(){
        super();
        addRoot(null);
    }
    

    /**
   * Insert the Point3D object in the tree based its dimension,
   * @param key  the Point3D object is to be inserted to the tree
   */
    public void insert(Point3D key) {
        Point3D newPoint = new Point3D(key.x,key.y,key.z);
        Position<Point3D> p = treeSearch(root(), key);
        if(isExternal(p)) {
            expandExternal(p, newPoint);
        }
        else {
            set(p,newPoint);
        }
    }
    /**
   * Removes the Point3D object in the tree based its dimension,
   * @param key  the Point3D object is to be removed from the tree
   */
    public void remove(Point3D key) {
        Position<Point3D> p = treeSearch(root(), key);
        if (isExternal(p)) return;
        else{
            if(isInternal(p)) {
                Position<Point3D> replacement=this.validate(p);
                int dim = depth(p) % 3;
                while(isInternal(replacement)){
                    if (isInternal(left(replacement)) && isInternal(right(replacement)) || (isExternal(left(replacement)) && isInternal(right(replacement)))) { // both children are internal
                        replacement = treeMin(right(replacement),dim);
                    }
                    else if(isInternal(left(replacement)) && isExternal(right(replacement))){
                        replacement = treeMax(left(replacement),dim);
                    }
                    else if(isExternal(left(replacement)) && isExternal(right(replacement))) {
                        break;
                    }
                    set(p, replacement.getElement());
                    p = replacement;
                }
                remove(left(p));
                remove(right(p));
                set(p, null);
            }
        }
                                     
    }

    /**
     * Returns the "Point3D", or null if no such point exists.
     *
     * @param key the point is to be returned
     * @return the associated point, or null if no such point exists
     */
    public Point3D search(Point3D key) {
        Position<Point3D> p = treeSearch(root(), key);
        if (isExternal(p)) {
            return null;         // unsuccessful search
        }
        return p.getElement();       // match found
    }
    /**
   * Returns the point with the smallest value basen on its dimension.
   * @param d  d=0 represent the x dimension, d=1 represent the y dimension, and d=2 represent the z dimension.
   * @return Returns the point with the smallest value in the d-th dimension.
   * 
   */
    public Point3D findMin(int d) {
        Point3D min=root().getElement();
        for(Position<Point3D> p:inorder()) {
            Point3D cur=p.getElement();
            if(cur!=null){
                if(  d==0 && min.x>cur.x) min=cur;
                else if(  d==1 && min.y>cur.y) min=cur;
                else if( d==2 && min.z>cur.z) min=cur;
            }
        }
        return min;
    }
    /**
   * Returns the point with the maximum value basen on its dimension.
   * @param d  d=0 represent the x dimension, d=1 represent the y dimension, and d=2 represent the z dimension.
   * @return Returns the point with the maximum value in the d-th dimension.
   * d=0 represent the x dimension, d=1 represent the y dimension, and d=2 represent the z dimension.
   */
    public Point3D findMax(int d) {
        Point3D max=root().getElement();
        for(Position<Point3D> p:inorder()) {
            Point3D cur=p.getElement();
            if(cur!=null){
                if(d==0 && max.x<cur.x) max=cur;
                else if(d==1 && max.y<cur.y) max=cur;
                else if(  d==2 && max.z<cur.z) max=cur;
            }
        }
        return max;
    }

    /**
     *@param fll  front lower left corner
     *@param bur  back upper right corner
     * fll and bur specify specifies a rectangular box. 
     * This method print all the points in the tree that lie within the given box, one point per line.                                                                                                    
     */
    public void printRange(Point3D fll, Point3D bur) {
        for(Position<Point3D> p:inorder()) {
            Point3D cur=p.getElement();
            if(cur!=null){
                if( (cur.x>=fll.x && cur.x<=bur.x) && (cur.y>=fll.y && cur.y<=bur.y) && (cur.z>=fll.z && cur.z<=bur.z) ) {
                    System.out.println(p.getElement());
                }
            }
        }
    }
    
    /**
     * Print the tree in preorder traversal to console.
     * p                                                                           
     * . p1                                                                                                                                                 
     * . . p2
     * . p1_1                                                                      
     * goes like this.                                                                                                         
     */
    public void displayTree() {
        for(Position<Point3D> p:preorder()) {
            String s="";
            int depth=depth(p);
            for(int i = 0; i<depth;i++) s+=". ";
            if(p.getElement()!=null)
                System.out.println(s+p.getElement());
        }
    }
    
    /**
     * Utility used when inserting a new entry at a leaf of the tree
     */
    private void expandExternal(Position<Point3D> p, Point3D entry) {
        set(p, entry);            // store new point(entry) at p
        addLeft(p, null);         // add new sentinel leaves as children
        addRight(p, null);
    }

     /**
   * Returns the position in p's subtree having the given key (or else the terminal leaf).
   * @param key  a target point
   * @param p  a position of the tree serving as root of a subtree
   * @return Position holding key, or last node reached during search
   */
    private Position<Point3D> treeSearch(Position<Point3D> p, Point3D key) {
        if (isExternal(p)) return p; // key not found; return the final leaf
        
        Point3D tmp=p.getElement();
        if(tmp==null) return null;
        int mod = this.depth(p) % 3;
        if(mod==0){
            if(tmp.x>key.x) return treeSearch(left(p), key);
            else if(tmp.x<key.x) return treeSearch(right(p), key);
            else return p;
        }
        else if(mod==1){
            if(tmp.y>key.y) return treeSearch(left(p), key);
            else if(tmp.y<key.y) return treeSearch(right(p), key);
            else return p;
        }
        else{
            if(tmp.z>key.z) return treeSearch(left(p), key);
            else if(tmp.z<key.z) return treeSearch(right(p), key);
            else return p;
        }
    }

     /** 
    * It is the same as inorderSubtree method in AbstractBinaryTree class.
    * (It is implemented in order to not make any changes in AbstractBinaryTree )
    */
    private void subtreeInOrder(Position<Point3D> p, List<Position<Point3D>> snapshot) {
        if (left(p) != null)
            subtreeInOrder(left(p), snapshot);
        snapshot.add(p);
        if (right(p) != null)
            subtreeInOrder(right(p), snapshot);
    }

    /**
   * Returns the position with the maximum point based on its dimension in the subtree rooted at p.
   * @param p  a Position of the tree serving as root of a subtree
   * @return Position with the maximum point based on its dimension in subtree
   */
    private Position<Point3D> treeMax(Position<Point3D> p,int d) {
        Position<Point3D> max=p;
        List<Position<Point3D>> snapshot = new ArrayList<>();
        subtreeInOrder(p, snapshot);
        for(Position<Point3D> pos:snapshot) {
            Position<Point3D> cur=pos;
            if(isInternal(cur) )
            if(cur!=null){
                if(d==0 && max.getElement().x<cur.getElement().x) max=cur;
                else if(d==1 && max.getElement().y<cur.getElement().y) max=cur;
                else if(  d==2 && max.getElement().z<cur.getElement().z) max=cur;
            }
        }
        return max;
    }
    /**
   * Returns the position with the minumum point based on its dimension in the subtree rooted at p.
   * @param p  a Position of the tree serving as root of a subtree
   * @return Position with the minumum point based on its dimension in subtree
   */
    private Position<Point3D> treeMin(Position<Point3D> p,int d) {
        Position<Point3D> min=p;
        List<Position<Point3D>> snapshot = new ArrayList<>();
        subtreeInOrder(p, snapshot);
        for(Position<Point3D> pos:snapshot) {
            Position<Point3D> cur=pos;
            if(isInternal(cur) ){
                if(cur!=null){
                    if(d==0 && min.getElement().x>cur.getElement().x) min=cur;
                    else if(d==1 && min.getElement().y>cur.getElement().y) min=cur;
                    else if(  d==2 && min.getElement().z>cur.getElement().z) min=cur;
                }
            }
        }
        return min;
    }
   
    
    
    
}