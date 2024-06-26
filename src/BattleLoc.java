import java.util.Random;

public abstract class BattleLoc extends Location {
    private Obstacle obstacle;
    private String award;
    private int maxObstacle;

    public BattleLoc(Player player, String name, Obstacle obstacle, String award, int maxObstacle) {
        super(player, name);
        this.obstacle = obstacle;
        this.award = award;
        this.maxObstacle = maxObstacle;
    }

    @Override
    public boolean onLocation() {
        int obsNumber = this.randomObstacleNumber();

        System.out.println("Şuan buradasınız : " + this.getName());
        System.out.println("Dikkatli Ol! Burada " + obsNumber + " tane " + this.getObstacle().getName() + " yaşıyor !");


        // İlk hamlenin %50 şansla oyuncuya veya düşmana verilmesi
        boolean playerFirst = new Random().nextBoolean();
        if (playerFirst) {
            System.out.println("Oyuncu ilk hamleyi yapıyor!");
        } else {
            System.out.println("Düşman ilk hamleyi yapıyor!");
        }

        System.out.print("<S>avaş veya <K>aç : ");
        String selectCase = input.nextLine().toUpperCase();
        if (selectCase.equals("S") && combat(obsNumber, playerFirst)) {
            System.out.println(this.getName() + " tüm düşmanları yendiniz !");
            return true;
        }
        if (this.getPlayer().getHealth() <= 0) {
            System.out.println("Öldünüz !");
            return false;
        }

        return true;
    }

    public boolean combat(int obsNumber, boolean playerFirst) {
        for (int i = 1; i <= obsNumber; i++) {
            this.getObstacle().setHealth(this.getObstacle().getOrjinalHealth());
            playerStats();
            obstacleStats(i);

            while (this.getPlayer().getHealth() > 0 && this.getObstacle().getHealth() > 0) {
                if (playerFirst) {
                    // Oyuncunun ilk hamle yapması durumu
                    System.out.print("<V>ur veya <K>aç : ");
                    String selectCombat = input.nextLine().toUpperCase();
                    if (selectCombat.equals("V")) {
                        System.out.println("Siz vurdunuz !");
                        this.getObstacle().setHealth(this.obstacle.getHealth() - this.getPlayer().getTotalDamage());
                        afterHit();
                        if (this.getObstacle().getHealth() > 0) {
                            System.out.println();
                            System.out.println("Canavar Size Vurdu !");
                            int obstacleDamage = this.getObstacle().getDamage() - this.getPlayer().getInventory().getArmor().getBlock();
                            if (obstacleDamage < 0) {
                                obstacleDamage = 0;
                            }
                            this.getPlayer().setHealth(this.getPlayer().getHealth() - obstacleDamage);
                            afterHit();
                        }
                    } else {
                        return false;
                    }
                } else {
                    // Düşmanın ilk hamle yapması durumu
                    System.out.println("Düşman size saldırıyor!");
                    int obstacleDamage = this.getObstacle().getDamage() - this.getPlayer().getInventory().getArmor().getBlock();
                    if (obstacleDamage < 0) {
                        obstacleDamage = 0;
                    }
                    this.getPlayer().setHealth(this.getPlayer().getHealth() - obstacleDamage);
                    afterHit();

                    if (this.getPlayer().getHealth() > 0) {
                        System.out.print("<V>ur veya <K>aç : ");
                        String selectCombat = input.nextLine().toUpperCase();
                        if (selectCombat.equals("V")) {
                            System.out.println("Siz vurdunuz !");
                            this.getObstacle().setHealth(this.obstacle.getHealth() - this.getPlayer().getTotalDamage());
                            afterHit();
                            if (this.getObstacle().getHealth() > 0) {
                                System.out.println();
                                System.out.println("Canavar Size Vurdu !");
                                int obstacleDamage2 = this.getObstacle().getDamage() - this.getPlayer().getInventory().getArmor().getBlock();
                                if (obstacleDamage2 < 0) {
                                    obstacleDamage2 = 0;
                                }
                                this.getPlayer().setHealth(this.getPlayer().getHealth() - obstacleDamage2);
                                afterHit();
                            }
                        } else {
                            return false;
                        }
                    }
                }
            }
            if (this.getObstacle().getHealth() < this.getPlayer().getHealth()) {
                System.out.println("Düşmanı Yendiniz !");
                System.out.println("Ganimet kazandınız !");
                // Rastgele bir ödül kazanma
                int randomReward = new Random().nextInt(3); // 0, 1 veya 2
                switch (randomReward) {
                    case 0:
                        System.out.println("Para kazandınız !");
                        this.getPlayer().setMoney(this.getPlayer().getMoney() + 100); // Örnek olarak 100 para kazanma
                        break;
                    case 1:
                        System.out.println("Silah kazandınız !");
                        // Silah kazanma işlemi buraya eklenecek
                        break;
                    case 2:
                        System.out.println("Zırh kazandınız !");
                        // Zırh kazanma işlemi buraya eklenecek
                        break;
                }
                System.out.println("Güncel Paranız : " + this.getPlayer().getMoney());
            } else {
                return false;
            }
        }
        return true;

    }
    private void attack() {
        obstacle.setHealth(obstacle.getHealth() - getPlayer().getTotalDamage());
        afterHit();
    }
    public void afterHit() {
        System.out.println("Canınız : " + this.getPlayer().getHealth());
        System.out.println(this.getObstacle().getName() + " Canı :" + this.getObstacle().getHealth());
        System.out.println("---------------------------");

    }


    public void playerStats() {
        System.out.println("Oyuncu Değerleri");
        System.out.println("-------------------------");
        System.out.println("Sağlık : " + this.getPlayer().getHealth());
        System.out.println("Silah : " + this.getPlayer().getInventory().getWeapon().getName());
        System.out.println("Hasar : " + this.getPlayer().getTotalDamage());
        System.out.println("Zırh : " + this.getPlayer().getInventory().getArmor().getName());
        System.out.println("Bloklama : " + this.getPlayer().getInventory().getArmor().getBlock());
        System.out.println("Para : " + this.getPlayer().getMoney());
        System.out.println();

    }

    public void obstacleStats(int i) {
        System.out.println(i + "." + this.getObstacle().getName() + " Değerleri");
        System.out.println("-------------------------");
        System.out.println("Sağlık : " + this.getObstacle().getHealth());
        System.out.println("Hasar : " + this.getObstacle().getDamage());
        System.out.println("Ödül : " + this.getObstacle().getAward());
    }


    public int randomObstacleNumber() {
        Random r = new Random();
        return r.nextInt(this.getMaxObstacle()) + 1; //0 1 --> 1 2
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public int getMaxObstacle() {
        return maxObstacle;
    }

    public void setMaxObstacle(int maxObstacle) {
        this.maxObstacle = maxObstacle;
    }
}

