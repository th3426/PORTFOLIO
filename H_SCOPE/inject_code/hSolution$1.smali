###### Class com.tpcstld.twozerogame.hSolution.AnonymousClass1 (com.tpcstld.twozerogame.hSolution$1)
.class Lcom/tpcstld/twozerogame/hSolution$1;
.super Ljava/lang/Object;
.source "hSolution.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/tpcstld/twozerogame/hSolution;->onCreate()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/tpcstld/twozerogame/hSolution;


# direct methods
.method constructor <init>(Lcom/tpcstld/twozerogame/hSolution;)V
    .registers 2

    .line 38
    iput-object p1, p0, Lcom/tpcstld/twozerogame/hSolution$1;->this$0:Lcom/tpcstld/twozerogame/hSolution;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 6

    const-string v0, "hSolution"

    .line 42
    sget-object v1, Landroid/os/Build;->CPU_ABI:Ljava/lang/String;

    const-string v2, "inject_assets.jar"

    .line 44
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v4, p0, Lcom/tpcstld/twozerogame/hSolution$1;->this$0:Lcom/tpcstld/twozerogame/hSolution;

    invoke-virtual {v4}, Lcom/tpcstld/twozerogame/hSolution;->getFilesDir()Ljava/io/File;

    move-result-object v4

    invoke-virtual {v4}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    sget-object v4, Ljava/io/File;->pathSeparator:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, "unzip/inject_lib/assets/"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 50
    :try_start_2d
    iget-object v3, p0, Lcom/tpcstld/twozerogame/hSolution$1;->this$0:Lcom/tpcstld/twozerogame/hSolution;

    invoke-virtual {v3, v2, v1}, Lcom/tpcstld/twozerogame/hSolution;->nativeLibLoader(Ljava/lang/String;Ljava/lang/String;)V

    const-string v2, "loaded native lib"

    .line 51
    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_37
    .catch Ljava/io/IOException; {:try_start_2d .. :try_end_37} :catch_38

    goto :goto_41

    :catch_38
    move-exception v2

    .line 53
    invoke-virtual {v2}, Ljava/io/IOException;->printStackTrace()V

    .line 54
    iget-object v2, p0, Lcom/tpcstld/twozerogame/hSolution$1;->this$0:Lcom/tpcstld/twozerogame/hSolution;

    invoke-virtual {v2}, Lcom/tpcstld/twozerogame/hSolution;->exitApp()V

    .line 60
    :goto_41
    iget-object v2, p0, Lcom/tpcstld/twozerogame/hSolution$1;->this$0:Lcom/tpcstld/twozerogame/hSolution;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v3, "/libEdgeCrypto.sign"

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v2, v1}, Lcom/tpcstld/twozerogame/hSolution;->access$000(Lcom/tpcstld/twozerogame/hSolution;Ljava/lang/String;)I

    move-result v1

    iput v1, v2, Lcom/tpcstld/twozerogame/hSolution;->ret:I

    .line 62
    iget-object v1, p0, Lcom/tpcstld/twozerogame/hSolution$1;->this$0:Lcom/tpcstld/twozerogame/hSolution;

    iget v1, v1, Lcom/tpcstld/twozerogame/hSolution;->ret:I

    if-eqz v1, :cond_82

    .line 63
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "func:initCrypto() failed, ret : "

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v2, p0, Lcom/tpcstld/twozerogame/hSolution$1;->this$0:Lcom/tpcstld/twozerogame/hSolution;

    iget v2, v2, Lcom/tpcstld/twozerogame/hSolution;->ret:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 64
    iget-object v0, p0, Lcom/tpcstld/twozerogame/hSolution$1;->this$0:Lcom/tpcstld/twozerogame/hSolution;

    invoke-virtual {v0}, Lcom/tpcstld/twozerogame/hSolution;->exitApp()V

    goto :goto_87

    :cond_82
    const-string v1, "func:initCrypto() success "

    .line 67
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 73
    :goto_87
    iget-object v0, p0, Lcom/tpcstld/twozerogame/hSolution$1;->this$0:Lcom/tpcstld/twozerogame/hSolution;

    invoke-virtual {v0}, Lcom/tpcstld/twozerogame/hSolution;->getApplicationContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/tpcstld/twozerogame/hSolution;->getApkSignature(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/tpcstld/twozerogame/hSolution;->access$100(Lcom/tpcstld/twozerogame/hSolution;Ljava/lang/String;)I

    move-result v1

    iput v1, v0, Lcom/tpcstld/twozerogame/hSolution;->ret:I

    .line 75
    new-instance v0, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    .line 76
    new-instance v1, Lcom/tpcstld/twozerogame/hSolution$1$1;

    invoke-direct {v1, p0}, Lcom/tpcstld/twozerogame/hSolution$1$1;-><init>(Lcom/tpcstld/twozerogame/hSolution$1;)V

    const-wide/16 v2, 0x0

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    return-void
.end method
