package com.example.desarrollo_mobile.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.desarrollo_mobile.data.local.dao.CandidataDao
import com.example.desarrollo_mobile.data.local.dao.CriterioDao
import com.example.desarrollo_mobile.data.local.dao.EvaluacionDao
import com.example.desarrollo_mobile.data.local.dao.HistorialDao
import com.example.desarrollo_mobile.model.Candidata
import com.example.desarrollo_mobile.model.Criterio
import com.example.desarrollo_mobile.model.Evaluacion
import com.example.desarrollo_mobile.model.PuntajeCriterio
import com.example.desarrollo_mobile.model.RegistroHistorial
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Candidata::class,
        Criterio::class,
        Evaluacion::class,
        PuntajeCriterio::class,
        RegistroHistorial::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WimDatabase : RoomDatabase() {

    abstract fun candidataDao(): CandidataDao
    abstract fun criterioDao(): CriterioDao
    abstract fun evaluacionDao(): EvaluacionDao
    abstract fun historialDao(): HistorialDao

    companion object {
        @Volatile
        private var INSTANCE: WimDatabase? = null

        fun getDatabase(context: Context): WimDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WimDatabase::class.java,
                    "wim_100_database"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        database.criterioDao().insertarLote(DatosSemilla.CRITERIOS_INICIALES)
                    }
                }
            }
        }
    }
}